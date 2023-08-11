CREATE TABLE user_data (
    id BIGINT NOT NULL AUTO_INCREMENT,
    age INTEGER,
    birth_date DATE,
    password VARCHAR(255),
    username VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=MyISAM;

