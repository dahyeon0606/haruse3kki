CREATE TABLE user (
                      user_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
                      nickname    VARCHAR(45)  NOT NULL,
                      birthday    DATE,
                      provider    VARCHAR(45)  NOT NULL,
                      provider_id VARCHAR(100) NOT NULL UNIQUE,
                      created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE couple (
                        couple_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user1_id   BIGINT NOT NULL,
                        user2_id   BIGINT NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_couple_user1 FOREIGN KEY (user1_id) REFERENCES user(user_id),
                        CONSTRAINT fk_couple_user2 FOREIGN KEY (user2_id) REFERENCES user(user_id)
);

CREATE TABLE meal (
                      meal_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_id    BIGINT       NOT NULL,
                      meal_type  VARCHAR(45)  NOT NULL,
                      photo_url  VARCHAR(255),
                      content    VARCHAR(500),
                      comment    VARCHAR(45),
                      eaten_at   DATETIME,
                      date       DATE         NOT NULL,
                      created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      CONSTRAINT fk_meal_user FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE INDEX idx_meal_user_date ON meal(user_id, date);