
ALTER TABLE completed_game ADD CONSTRAINT FKjas8719ejn5kx0oyyhdvwkupd FOREIGN KEY (game_id) REFERENCES game (id);
ALTER TABLE completed_game ADD CONSTRAINT FK1gt4jvy1sev6pa5dfvv1jycj4 FOREIGN KEY (ranking_per_gamer_id) REFERENCES ranking_per_gamer (id);
ALTER TABLE ranking_per_gamer ADD CONSTRAINT FKc3931f5ncg7fiw2s74nobpymn FOREIGN KEY (user_id) REFERENCES user (id);

