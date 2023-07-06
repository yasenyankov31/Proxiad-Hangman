package com.game_classes.interfaces.jpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.game_classes.models.game.RankingPerGamer;

public interface RankingRepository extends JpaRepository<RankingPerGamer, Long> {}
