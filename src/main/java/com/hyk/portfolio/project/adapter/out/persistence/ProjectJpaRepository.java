package com.hyk.portfolio.project.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectJpaRepository extends JpaRepository<ProjectJpaEntity, Long> {

  boolean existsBySlug(String slug);

}
