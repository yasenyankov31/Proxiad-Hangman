package com.game_classes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.game_classes.interfaces.RankingRepository;
import com.game_classes.models.CompletedGame;
import com.game_classes.models.RankingPerGamer;
import com.game_classes.models.User;

@Repository
public class RankingRepositoryImpl implements RankingRepository {
  @PersistenceContext EntityManager entityManager;

  @Transactional
  @Override
  public void createUser(User user) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    Root<User> root = countQuery.from(User.class);

    // Apply your criteria to the count query
    // Example: Adding a WHERE clause to filter by a specific property
    countQuery
        .select(criteriaBuilder.count(root))
        .where(criteriaBuilder.equal(root.get("username"), user.getUsername()));

    Long count = entityManager.createQuery(countQuery).getSingleResult();
    System.out.println(count);
    if (count == 0) {
      entityManager.persist(user);
    }
  }

  @Transactional
  @Override
  public void createStatisticRecord(CompletedGame completedGame, RankingPerGamer gameStatistic) {
    entityManager.persist(gameStatistic);
    entityManager.persist(completedGame);
  }
}
