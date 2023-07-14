package com.game_classes.interfaces.jpaRepositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game_classes.models.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {

	Page<UserData> findAll(Pageable pageable);

	List<UserData> findAllByUsername(String Username);

}
