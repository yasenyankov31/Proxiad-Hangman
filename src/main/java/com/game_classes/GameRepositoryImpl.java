package com.game_classes;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.game_classes.interfaces.GameRepository;
import com.game_classes.models.game.CompletedGame;
import com.game_classes.models.game.Game;

@Repository
@Transactional
public class GameRepositoryImpl implements GameRepository {

  @PersistenceContext
  EntityManager entityManager;

  private List<Long> queue = new ArrayList<>();

  @Override
  public void createGame(Game game) {
    entityManager.persist(game);
  }

  public void updateGameHibernate(Game game) {
    Session session = entityManager.unwrap(Session.class);
    session.update(game);
  }

  public Game findByIdHibernate(long gameId) {
    Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Game.class);
    criteria.add(Restrictions.eq("id", gameId));

    return (Game) criteria.uniqueResult();
  }

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

  @Override
  public void updateGame(Game game) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaUpdate<Game> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Game.class);
    Root<Game> root = criteriaUpdate.from(Game.class);
    // Set the update values for all properties
    criteriaUpdate.set("word", game.getWord()).set("guessedWord", game.getGuessedWord())
        .set("lettersUsed", game.getLetters()).set("attemptsLeft", game.getAttemptsLeft())
        .set("wordNum", game.getWordNum())
        .where(criteriaBuilder.equal(root.get("id"), game.getId()));

    // Execute the update query
    entityManager.createQuery(criteriaUpdate).executeUpdate();
  }

  @Override
  public void deleteGame(Game game) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaDelete<Game> criteriaQuery = criteriaBuilder.createCriteriaDelete(Game.class);
    Root<Game> root = criteriaQuery.from(Game.class);

    criteriaQuery.where(criteriaBuilder.equal(root.get("id"), game.getId()));

    entityManager.createQuery(criteriaQuery).executeUpdate();
  }

  @Override
  public Page<Game> getUnfinishedGames(Long userId, Pageable pageable) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Game> query = criteriaBuilder.createQuery(Game.class);
    Root<Game> gameRoot = query.from(Game.class);
    Join<Game, CompletedGame> completedGameJoin = gameRoot.join("completedGame", JoinType.LEFT);

    // Add condition to filter by user_game_id
    Predicate userGameIdPredicate = criteriaBuilder.equal(gameRoot.get("userGame"), userId);

    Predicate completedGameIdIsNull = criteriaBuilder.isNull(completedGameJoin.get("id"));

    query.select(gameRoot).where(criteriaBuilder.and(userGameIdPredicate, completedGameIdIsNull));

    query.orderBy(criteriaBuilder.asc(gameRoot.get("id")));

    TypedQuery<Game> typedQuery = entityManager.createQuery(query);

    // Get the total number of rows without pagination
    List<Game> totalResults = entityManager.createQuery(query).getResultList();
    int totalRows = totalResults.size();

    // Set pagination parameters
    typedQuery.setFirstResult((int) pageable.getOffset());
    typedQuery.setMaxResults(pageable.getPageSize());

    List<Game> games = typedQuery.getResultList();

    return new PageImpl<>(games, pageable, totalRows);
  }

  @Override
  public Page<Game> getUnfinishedGames(Pageable pageable) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Game> query = criteriaBuilder.createQuery(Game.class);
    Root<Game> gameRoot = query.from(Game.class);
    Join<Game, CompletedGame> completedGameJoin = gameRoot.join("completedGame", JoinType.LEFT);

    query.select(gameRoot).where(criteriaBuilder.isNull(completedGameJoin.get("id")));

    // Apply pagination to the query
    query.orderBy(criteriaBuilder.asc(gameRoot.get("id")));

    TypedQuery<Game> typedQuery = entityManager.createQuery(query);

    // Get the total number of rows without pagination
    List<Game> totalResults = entityManager.createQuery(query).getResultList();
    int totalRows = totalResults.size();

    // Set pagination parameters
    typedQuery.setFirstResult((int) pageable.getOffset());
    typedQuery.setMaxResults(pageable.getPageSize());

    List<Game> games = typedQuery.getResultList();

    return new PageImpl<>(games, pageable, totalRows);
  }

  @Override
  public List<Game> getUnfinishedGames() {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Game> query = criteriaBuilder.createQuery(Game.class);
    Root<Game> gameRoot = query.from(Game.class);
    Join<Game, CompletedGame> completedGameJoin = gameRoot.join("completedGame", JoinType.LEFT);

    query.select(gameRoot).where(criteriaBuilder.isNull(completedGameJoin.get("id")));

    // Apply pagination to the query
    query.orderBy(criteriaBuilder.desc(gameRoot.get("date")));

    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public boolean checkIfGameIsCompleted(long gameId) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<CompletedGame> criteriaQuery = criteriaBuilder.createQuery(CompletedGame.class);
    Root<CompletedGame> root = criteriaQuery.from(CompletedGame.class);

    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("game"), gameId));

    return entityManager.createQuery(criteriaQuery).getResultList().size() == 1;
  }
}
