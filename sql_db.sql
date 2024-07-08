USE YOUR_DATABASE;
  -- >>>>>>>>>>>> change this line so it uses your database, not mine <<<<<<<<<<<<<<<
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS quizzesHistory;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS questions;
 -- remove table if it already exists and start from scratch

CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT,
    email_address CHAR(64),
    username CHAR(64),
    password_hash CHAR(64),
    status_ CHAR(64),
    primary key(account_id)
);

CREATE TABLE friends (
    friendship_id INT AUTO_INCREMENT,
    account_id INT,
    fr_account_id INT,
    friendship_date Timestamp,
    PRIMARY KEY(friendship_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (fr_account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    notification_id INT AUTO_INCREMENT,
    account_id INT,
    from_account_id INT,
    notification_type CHAR(64),
    notification_text CHAR(64),
    notification_date Timestamp,
    PRIMARY KEY (notification_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (from_account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

CREATE TABLE achievements (
      achievement_id INT AUTO_INCREMENT,
      account_id int,
      achievement_type CHAR(64),
      achievement_text VARCHAR(225),
      PRIMARY KEY(achievement_id),
      FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

CREATE TABLE quizzesHistory (
    quizzesHistory_id INT AUTO_INCREMENT,
    quiz_id int,
    account_id int,
    score int,
    done_date TIMESTAMP,
    primary key (quizzesHistory_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);


CREATE TABLE quizzes (
                         quiz_id INT AUTO_INCREMENT,
                         account_id INT,
                         quiz_title VARCHAR(255),
                         quiz_category VARCHAR(64),
                         quiz_creation_date DATE,
                         num_completed INT,
                         quiz_timer VARCHAR(64),
                         quiz_points INT,
                         quiz_photo VARCHAR(255),
                         PRIMARY KEY(quiz_id),
                         FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

CREATE TABLE questions (
                           question_id INT AUTO_INCREMENT,
                           quiz_id INT,
                           question_text TEXT,
                           question_type VARCHAR(64),
                           question_photo VARCHAR(255),
                           PRIMARY KEY (question_id),
                           FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

CREATE TABLE answers (
                         answer_id INT AUTO_INCREMENT,
                         question_id INT,
                         answer_text TEXT,
                         is_correct BOOLEAN,
                         answer_photo VARCHAR(255),
                         PRIMARY KEY (answer_id),
                         FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);
