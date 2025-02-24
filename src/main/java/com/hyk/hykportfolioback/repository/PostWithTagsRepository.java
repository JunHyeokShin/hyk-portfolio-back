package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostWithTagsRepository extends JpaRepository<PostWithTagsEntity, Integer> {

  List<PostWithTagsEntity> findAllByOrderByCreatedAtDesc();

}
