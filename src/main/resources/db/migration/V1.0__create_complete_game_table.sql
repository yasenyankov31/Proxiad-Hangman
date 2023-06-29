-- Create tables
CREATE TABLE completed_game (
    id BIGINT NOT NULL AUTO_INCREMENT,
    finish_date DATETIME,
    game_status VARCHAR(255),
    game_id BIGINT,
    ranking_per_gamer_id BIGINT,
    PRIMARY KEY (id)
) ENGINE=MyISAM;
