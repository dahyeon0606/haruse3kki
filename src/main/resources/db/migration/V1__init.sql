CREATE TABLE user (
                      user_id     INT AUTO_INCREMENT PRIMARY KEY,
                      email       VARCHAR(45)  NOT NULL UNIQUE,
                      nickname    VARCHAR(45)  NOT NULL,
                      birthday    DATE,
                      provider    VARCHAR(45)  NOT NULL,
                      provider_id VARCHAR(100) NOT NULL,
                      created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE couple (
                        couple_id  INT AUTO_INCREMENT PRIMARY KEY,
                        user1_id   INT      NOT NULL,
                        user2_id   INT      NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user1_id) REFERENCES user(user_id),
                        FOREIGN KEY (user2_id) REFERENCES user(user_id)
);

CREATE TABLE meal (
                      meal_id    INT AUTO_INCREMENT PRIMARY KEY,
                      user_id    INT          NOT NULL,
                      meal_type  VARCHAR(45)  NOT NULL,
                      photo_url  VARCHAR(255),
                      content    VARCHAR(500),
                      comment    VARCHAR(45),
                      eaten_at   DATETIME,
                      date       DATE         NOT NULL,
                      created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE INDEX idx_meal_user_date ON meal(user_id, date);