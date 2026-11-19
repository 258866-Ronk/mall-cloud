INSERT IGNORE INTO product_category(id,parent_id,category_name,level,sort,status) VALUES
(1,0,'手机',1,1,1),(2,0,'电脑',1,2,1),(3,0,'家电',1,3,1),(4,0,'服装',1,4,1);
INSERT IGNORE INTO product(id,category_id,product_name,subtitle,description,price,main_image,status,create_time,update_time) VALUES
(1001,1,'华为 Mate 70','旗舰影像手机','华为旗舰智能手机，支持高速网络和专业影像。',5999.00,'https://example.com/huawei-mate70.jpg',1,NOW(),NOW()),
(1002,1,'iPhone 15','A16 仿生芯片','Apple iPhone 15 128GB。',5299.00,'https://example.com/iphone15.jpg',1,NOW(),NOW()),
(1003,2,'联想 ThinkBook 14+','高性能轻薄本','14.5 英寸高性能办公笔记本。',6499.00,'https://example.com/thinkbook14.jpg',1,NOW(),NOW());
