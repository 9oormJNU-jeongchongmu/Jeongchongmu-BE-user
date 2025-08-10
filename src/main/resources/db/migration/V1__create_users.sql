CREATE TABLE users (
                       user_no            BIGINT       NOT NULL AUTO_INCREMENT,
                       oauth_provider     VARCHAR(20)  NOT NULL,
                       oauth_id           VARCHAR(100) NOT NULL,
                       name               VARCHAR(50)  NOT NULL,
                       university         VARCHAR(100) NOT NULL,
                       unit_type          VARCHAR(20)  NOT NULL,
                       unit_name          VARCHAR(50)  NOT NULL,
                       profile_image_url  VARCHAR(255)      NULL,
                       role               VARCHAR(20)  NOT NULL DEFAULT 'SECRETARY',
                       onboarded          TINYINT(1)   NOT NULL DEFAULT 0,
                       last_login_at      DATETIME          NULL,
                       created_at         DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at         DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT pk_users PRIMARY KEY (user_no),
                       CONSTRAINT uq_users_oauth UNIQUE (oauth_provider, oauth_id),
                       INDEX idx_users_univ_unit (university, unit_type, unit_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
