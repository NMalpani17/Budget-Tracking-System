CREATE TABLE IF NOT EXISTS `user_roles` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id int,
    role VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES `user`(id)
);