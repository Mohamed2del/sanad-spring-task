CREATE TABLE currencies (
                            id BIGSERIAL  PRIMARY KEY,
                            code VARCHAR(10) UNIQUE NOT NULL
);
