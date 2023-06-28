-- Create tables
CREATE TABLE completed_game (
    id BIGINT NOT NULL AUTO_INCREMENT,
    finish_date DATETIME,
    game_status VARCHAR(255),
    game_id BIGINT,
    ranking_per_gamer_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=MyISAM;

CREATE TABLE game (
    id BIGINT NOT NULL,
    attempts_left INTEGER NOT NULL,
    date DATETIME,
    guessed_word VARCHAR(255),
    letters_used VARCHAR(255),
    opponent_id BIGINT NOT NULL,
    word VARCHAR(255),
    word_num INTEGER NOT NULL,
    PRIMARY KEY (id)
) ENGINE=MyISAM;

CREATE TABLE ranking_per_gamer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_data_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=MyISAM;

CREATE TABLE user_data (
    id BIGINT NOT NULL AUTO_INCREMENT,
    age INTEGER,
    birth_date DATE,
    password VARCHAR(255),
    username VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=MyISAM;

