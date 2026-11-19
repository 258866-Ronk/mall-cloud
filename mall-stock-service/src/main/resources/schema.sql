CREATE TABLE IF NOT EXISTS stock (
 id BIGINT PRIMARY KEY, product_id BIGINT NOT NULL, total_stock INT NOT NULL, available_stock INT NOT NULL, locked_stock INT NOT NULL DEFAULT 0,
 version INT NOT NULL DEFAULT 0, update_time DATETIME NOT NULL, UNIQUE KEY uk_stock_product(product_id), CHECK(available_stock>=0), CHECK(locked_stock>=0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS stock_reservation (
 id BIGINT PRIMARY KEY, order_no VARCHAR(32) NOT NULL, product_id BIGINT NOT NULL, quantity INT NOT NULL, status VARCHAR(20) NOT NULL,
 create_time DATETIME NOT NULL, update_time DATETIME NOT NULL, UNIQUE KEY uk_reservation_order_product(order_no,product_id), KEY idx_reservation_status(status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
