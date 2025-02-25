package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.PostTagEntity;
import com.hyk.hykportfolioback.entity.primaryKey.PostTagPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTagEntity, PostTagPk> {

  void deleteAllByPostId(Integer postId);

}
