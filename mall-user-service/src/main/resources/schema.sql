CREATE TABLE IF NOT EXISTS mall_user (
  id BIGINT PRIMARY KEY,
  username VARCHAR(32) NOT NULL,
  password VARCHAR(100) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(100),
  status TINYINT NOT NULL DEFAULT 1,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  create_time DATETIME NOT NULL,
  update_time DATETIME NOT NULL,
  UNIQUE KEY uk_user_username (username),
  UNIQUE KEY uk_user_phone (phone),
  KEY idx_user_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
