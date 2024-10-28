CREATE SCHEMA product_schema;
CREATE TABLE product_schema.product_price
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id   BIGINT         NOT NULL,
    start_date TIMESTAMP      NOT NULL,
    end_date   TIMESTAMP      NOT NULL,
    price_list BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    priority   INT            NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    currency   VARCHAR(3)     NOT NULL
);

CREATE INDEX idx_product_price_lookup ON product_schema.product_price(product_id, brand_id, start_date, end_date);
CREATE INDEX idx_product_price_priority ON product_schema.product_price(priority DESC);