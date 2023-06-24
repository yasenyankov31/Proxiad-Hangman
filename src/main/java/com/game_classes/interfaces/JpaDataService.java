package com.game_classes.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.game_classes.models.CompletedGame;

public interface JpaDataService extends JpaRepository<CompletedGame, Long> {
  @Query("SELECT cg.id FROM CompletedGame cg WHERE cg.gameStatus = 'Won' ORDER BY cg.id DESC")
  List<Long> allWinsOfAllPlayers();
}
