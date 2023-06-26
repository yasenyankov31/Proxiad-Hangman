package com.game_classes.interfaces.JpaRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.game_classes.models.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
  public UserData findByUsername(String username);
}
