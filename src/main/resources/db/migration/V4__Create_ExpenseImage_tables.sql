CREATE TABLE IF NOT EXISTS `expense_image` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image LONGBLOB,
    type VARCHAR(255) NOT NULL,
    expense_id INT,
    FOREIGN KEY (expense_id) REFERENCES `expense`(id)
);