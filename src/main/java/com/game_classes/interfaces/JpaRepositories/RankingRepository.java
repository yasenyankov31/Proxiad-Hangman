package com.game_classes.interfaces.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game_classes.models.RankingPerGamer;

public interface RankingRepository extends JpaRepository<RankingPerGamer, Long> {

}
