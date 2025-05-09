CREATE TABLE exchange_rate_logs (
                                    id BIGSERIAL PRIMARY KEY,
                                    currency_code VARCHAR(10) NOT NULL,
                                    rate DOUBLE PRECISION NOT NULL,
                                    timestamp TIMESTAMP NOT NULL
);
