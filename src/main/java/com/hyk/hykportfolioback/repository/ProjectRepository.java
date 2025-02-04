package com.hyk.hykportfolioback.repository;

import com.hyk.hykportfolioback.entity.ProjectEntity;
import com.hyk.hykportfolioback.repository.resultSet.GetProjectListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

  List<GetProjectListResultSet> findProjectListBy();

}
