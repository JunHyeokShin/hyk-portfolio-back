package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {

  TagEntity findByName(String name);

  @Modifying
  @Query(value = "DELETE T " +
      "FROM tag AS T " +
      "LEFT JOIN post_tag PT ON T.id = PT.tag_id " +
      "WHERE PT.tag_id IS NULL",
      nativeQuery = true)
  void deleteOrphanTags();

}
