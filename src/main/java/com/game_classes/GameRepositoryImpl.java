package com.game_classes;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.game_classes.interfaces.GameRepository;
import com.game_classes.models.Game;

@Repository
public class GameRepositoryImpl implements GameRepository {

  @PersistenceContext EntityManager entityManager;

  private List<Long> queue = new ArrayList<>();

  @Override
  @Transactional
  public void createGame(Game game) {
    entityManager.persist(game);
  }

  @Transactional
  @Override
  public Game findById(long gameId) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Game> criteriaQuery = criteriaBuilder.createQuery(Game.class);
    Root<Game> root = criteriaQuery.from(Game.class);

    // Apply your criteria to the query
    // Example: Adding a WHERE clause to filter by a specific property
    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), gameId));

    return entityManager.createQuery(criteriaQuery).getSingleResult();
  }

  @Override
  public void addToQueue(long id) {
    queue.add(id);
  }

  @Override
  public long getFromQueue() {
    if (queue.isEmpty()) {
      return 0;
    }
    long id = queue.get(0);
    queue.remove(0);
    return id;
  }

  @Transactional
  @Override
  public void updateGame(Game game) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaUpdate<Game> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Game.class);
    Root<Game> root = criteriaUpdate.from(Game.class);
    // Set the update values for all properties
    criteriaUpdate
        .set("word", game.getWord())
        .set("guessedWord", game.getGuessedWord())
        .set("lettersUsed", game.getLetters())
        .set("attemptsLeft", game.getAttemptsLeft())
        .set("wordNum", game.getWordNum())
        .where(criteriaBuilder.equal(root.get("id"), game.getId()));

    // Execute the update query
    entityManager.createQuery(criteriaUpdate).executeUpdate();
  }

  @Transactional
  @Override
  public void deleteGame(Game game) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaDelete<Game> criteriaQuery = criteriaBuilder.createCriteriaDelete(Game.class);
    Root<Game> root = criteriaQuery.from(Game.class);

    criteriaQuery.where(criteriaBuilder.equal(root.get("id"), game.getId()));

    entityManager.createQuery(criteriaQuery).executeUpdate();
  }
}
