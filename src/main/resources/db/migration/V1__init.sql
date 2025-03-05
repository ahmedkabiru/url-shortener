CREATE TABLE IF NOT EXISTS urls (
    id SERIAL PRIMARY KEY,
    short_code VARCHAR(10) NOT NULL,
    long_url VARCHAR NOT NULL,
    expiration_date TIMESTAMP,
    created_date TIMESTAMP NOT NULL
);