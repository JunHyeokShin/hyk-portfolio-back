package com.hyk.portfolio.project.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

  Optional<ProjectJpaEntity> findBySlug(String slug);

  boolean existsBySlug(String slug);

}
