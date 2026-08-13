package com.microservices.currency_exchange_service;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;


@Configuration
public class H2ConsoleConfig {

    private static final Logger logger = LoggerFactory.getLogger(H2ConsoleConfig.class);
    private Server webServer;

    @Value("${server.port:8000}")
    private int serverPort;

    @Autowired
    private io.r2dbc.spi.ConnectionFactory connectionFactory;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        // Ping the database to force H2 to create it immediately (R2DBC is lazy by default)
        reactor.core.publisher.Mono.from(connectionFactory.create())
            .flatMap(connection -> reactor.core.publisher.Mono.from(connection.close()))
            .subscribe();

        // Offset the H2 port by 10000 from the app port (e.g., 8000 -> 18000, 8001 -> 18001)
        int h2Port = serverPort + 10000;
        this.webServer = Server.createWebServer("-webPort", String.valueOf(h2Port), "-tcpAllowOthers").start();
        logger.info("H2 Web Console started on port {} (offset from app port {})", h2Port, serverPort);
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        if (this.webServer != null) {
            this.webServer.stop();
        }
    }
}
