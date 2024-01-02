CREATE TABLE IF NOT EXISTS `simplify` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lender_id INT,
    borrower_id INT,
    amount DECIMAL(10, 4) NOT NULL,
    group_id INT,
    FOREIGN KEY (lender_id) REFERENCES `user`(id),
    FOREIGN KEY (borrower_id) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
);