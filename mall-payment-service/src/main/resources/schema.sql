CREATE TABLE IF NOT EXISTS payment (
 id BIGINT PRIMARY KEY,payment_no VARCHAR(32) NOT NULL,order_no VARCHAR(32) NOT NULL,user_id BIGINT NOT NULL,amount DECIMAL(12,2) NOT NULL,pay_type VARCHAR(20) NOT NULL,status VARCHAR(20) NOT NULL,transaction_no VARCHAR(64),create_time DATETIME NOT NULL,pay_time DATETIME,
 UNIQUE KEY uk_payment_no(payment_no),UNIQUE KEY uk_payment_order(order_no),KEY idx_payment_user_time(user_id,create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE IF NOT EXISTS payment_outbox (
 id BIGINT PRIMARY KEY,message_id VARCHAR(64) NOT NULL,payment_no VARCHAR(32) NOT NULL,status VARCHAR(20) NOT NULL,retry_count INT NOT NULL DEFAULT 0,next_retry_time DATETIME NOT NULL,create_time DATETIME NOT NULL,sent_time DATETIME,
 UNIQUE KEY uk_outbox_message(message_id),KEY idx_outbox_status_retry(status,next_retry_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
