CREATE TABLE IF NOT EXISTS seckill_activity (
 id BIGINT PRIMARY KEY,product_id BIGINT NOT NULL,activity_name VARCHAR(100) NOT NULL,seckill_price DECIMAL(12,2) NOT NULL,total_stock INT NOT NULL,limit_per_user INT NOT NULL DEFAULT 1,start_time DATETIME NOT NULL,end_time DATETIME NOT NULL,status TINYINT NOT NULL DEFAULT 1,create_time DATETIME NOT NULL,
 KEY idx_activity_time_status(start_time,end_time,status),KEY idx_activity_product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
