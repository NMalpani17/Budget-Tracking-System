CREATE TABLE IF NOT EXISTS `user` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `group` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `user_group` (
    user_id INT,
    group_id INT,
    PRIMARY KEY (user_id, group_id),
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
);

CREATE TABLE IF NOT EXISTS `expense` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payer_id INT,
    group_id INT,
    date DATE,
    FOREIGN KEY (payer_id) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id)
);

CREATE TABLE IF NOT EXISTS `debt` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lender_id INT,
    borrower_id INT,
    amount DECIMAL(10, 4) NOT NULL,
    group_id INT,
    expense_id INT,
    FOREIGN KEY (lender_id) REFERENCES `user`(id),
    FOREIGN KEY (borrower_id) REFERENCES `user`(id),
    FOREIGN KEY (group_id) REFERENCES `group`(id),
    FOREIGN KEY (expense_id) REFERENCES `expense`(id)
);