CREATE TABLE IF NOT EXISTS mall_order (
 id BIGINT PRIMARY KEY,order_no VARCHAR(32) NOT NULL,request_no VARCHAR(64) NOT NULL,user_id BIGINT NOT NULL,total_amount DECIMAL(12,2) NOT NULL,pay_amount DECIMAL(12,2) NOT NULL,status VARCHAR(32) NOT NULL,
 receiver_name VARCHAR(64) NOT NULL,receiver_phone VARCHAR(20) NOT NULL,receiver_address VARCHAR(255) NOT NULL,create_time DATETIME NOT NULL,pay_time DATETIME,ship_time DATETIME,finish_time DATETIME,cancel_time DATETIME,
 UNIQUE KEY uk_order_no(order_no),UNIQUE KEY uk_order_user_request(user_id,request_no),KEY idx_order_user_time(user_id,create_time),KEY idx_order_status_time(status,create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS order_item (
 id BIGINT PRIMARY KEY,order_id BIGINT NOT NULL,order_no VARCHAR(32) NOT NULL,product_id BIGINT NOT NULL,product_name VARCHAR(120) NOT NULL,product_image VARCHAR(500),product_price DECIMAL(12,2) NOT NULL,quantity INT NOT NULL,total_amount DECIMAL(12,2) NOT NULL,
 KEY idx_item_order(order_no),KEY idx_item_product(product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS processed_message (
 id BIGINT PRIMARY KEY,message_id VARCHAR(64) NOT NULL,consumer VARCHAR(64) NOT NULL,processed_time DATETIME NOT NULL,UNIQUE KEY uk_message_consumer(message_id,consumer)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
