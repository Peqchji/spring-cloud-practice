CREATE TABLE IF NOT EXISTS currency_exchange (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_from VARCHAR(50) NOT NULL,
    currency_to VARCHAR(50) NOT NULL,
    conversion_multiple DECIMAL(10,2) NOT NULL
);
