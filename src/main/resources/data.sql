DROP TABLE IF EXISTS basket_coupon;
DROP TABLE IF EXISTS basket;
DROP TABLE IF EXISTS coupon;

CREATE TABLE coupon
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    code           VARCHAR(250)  NOT NULL UNIQUE,
    discount       DECIMAL(10, 2) NOT NULL,
    min_basket_value DECIMAL(10, 2) DEFAULT NULL
);

INSERT INTO coupon (code, discount, min_basket_value)
VALUES ('TEST1', 10.00, 50.00),
       ('TEST2', 15.00, 100.00),
       ('TEST3', 20.00, 200.00),
       ('TEST4', 5.00, 6.00);

CREATE TABLE basket
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) DEFAULT 0
);

INSERT INTO basket (id, amount)
VALUES (1, 75.00),
       (2, 120.00),
       (3, 250.00),
       (4, 30.00);

CREATE TABLE basket_coupon
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    basket_id        BIGINT            NOT NULL,
    coupon_id        BIGINT            NOT NULL,
    discount_applied DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (basket_id) REFERENCES basket (id) ON DELETE CASCADE,
    FOREIGN KEY (coupon_id) REFERENCES coupon (id),

    UNIQUE (basket_id, coupon_id)
);

INSERT INTO basket_coupon (basket_id, coupon_id, discount_applied)
VALUES (2, 1, 10.00),
       (3, 1, 10.00),
       (3, 2, 15.00);
