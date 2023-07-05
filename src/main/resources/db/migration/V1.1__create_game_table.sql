CREATE TABLE game (
    id BIGINT NOT NULL,
    attempts_left INTEGER NOT NULL,
    date DATETIME,
    guessed_word VARCHAR(255),
    letters_used VARCHAR(255),
    word VARCHAR(255),
    word_num INTEGER NOT NULL,
    PRIMARY KEY (id)
) ENGINE=MyISAM;