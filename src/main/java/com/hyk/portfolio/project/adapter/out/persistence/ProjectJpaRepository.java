package com.hyk.portfolio.project.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

  Optional<ProjectJpaEntity> findBySlug(String slug);

  Page<ProjectSummaryView> findAllBy(Pageable pageable);

  Page<ProjectSummaryView> findAllByTitleContainingOrDescriptionContaining(
      String title, String description, Pageable pageable);

  boolean existsBySlug(String slug);

}
