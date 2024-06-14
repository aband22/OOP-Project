USE YOUR_DATABASE;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<

DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS friends;
 -- remove table if it already exists and start from scratch

CREATE TABLE accounts (
    email_address CHAR(64),
    username CHAR(64),
    password_hash CHAR(64),
    primary key(email_address)
);

CREATE TABLE friends (
                         email_address CHAR(64),
                         fr_email_address CHAR(64),
                         PRIMARY KEY(email_address, fr_email_address),
                         FOREIGN KEY (email_address) REFERENCES accounts(email_address) ON DELETE CASCADE,
                         FOREIGN KEY (fr_email_address) REFERENCES accounts(email_address) ON DELETE CASCADE
);


CREATE TABLE quizzes (
                         quiz_id INT AUTO_INCREMENT,
                         email_address CHAR(64),
                         quiz_title VARCHAR(255),
                         quiz_category TEXT,
                         quiz_creation_date DATE,
                         num_completed INT,
                         PRIMARY KEY(quiz_id),
                         FOREIGN KEY (email_address) REFERENCES accounts(email_address) ON DELETE CASCADE

);