package com.vapitea.datacollector.repository;

import com.vapitea.datacollector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


  @Query("SELECT u FROM User u LEFT JOIN FETCH u.teams")
  List<User> findAllWithTeamsFetchedEagerly();

  @Query("SELECT u FROM User u LEFT JOIN FETCH u.teams WHERE u.id = :id")
  Optional<User> getOneWithTeamsFetchedEagerly(@Param("id") Long id);

  Optional<User> findByName(String username);

}
