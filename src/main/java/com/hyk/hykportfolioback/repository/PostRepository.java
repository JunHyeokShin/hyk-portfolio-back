package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {

  @Query(value = "SELECT AUTO_INCREMENT " +
      "FROM information_schema.TABLES " +
      "WHERE TABLE_SCHEMA = 'hyk_portfolio' " +
      "AND TABLE_NAME = 'post'", nativeQuery = true)
  Integer getNextAutoIncrementValue();

}
