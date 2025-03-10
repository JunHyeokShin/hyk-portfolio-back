package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

  List<ProjectEntity> findAllByOrderByCreatedAtDesc();

}
