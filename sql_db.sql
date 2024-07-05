USE YOUR_DATABASE;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS accounts;
 -- remove table if it already exists and start from scratch

CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT,
    email_address CHAR(64),
    username CHAR(64),
    password_hash CHAR(64),
    primary key(account_id)
);

CREATE TABLE friends (
    friendship_id INT AUTO_INCREMENT,
    account_id INT,
    fr_account_id INT,
    friendship_date DATE,
    PRIMARY KEY(friendship_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (fr_account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);


CREATE TABLE quizzes (
    quiz_id INT AUTO_INCREMENT,
    account_id int,
    quiz_title VARCHAR(255),
    quiz_category CHAR(64),
    quiz_creation_date DATE,
    num_completed INT,
    PRIMARY KEY(quiz_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);