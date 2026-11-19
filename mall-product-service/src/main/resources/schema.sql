CREATE TABLE IF NOT EXISTS product_category (
 id BIGINT PRIMARY KEY, parent_id BIGINT NOT NULL DEFAULT 0, category_name VARCHAR(64) NOT NULL, level INT NOT NULL DEFAULT 1,
 sort INT NOT NULL DEFAULT 0, status TINYINT NOT NULL DEFAULT 1, KEY idx_category_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS product (
 id BIGINT PRIMARY KEY, category_id BIGINT NOT NULL, product_name VARCHAR(120) NOT NULL, subtitle VARCHAR(255), description TEXT,
 price DECIMAL(12,2) NOT NULL, main_image VARCHAR(500), status TINYINT NOT NULL DEFAULT 1, create_time DATETIME NOT NULL, update_time DATETIME NOT NULL,
 KEY idx_product_category_status (category_id,status), KEY idx_product_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
