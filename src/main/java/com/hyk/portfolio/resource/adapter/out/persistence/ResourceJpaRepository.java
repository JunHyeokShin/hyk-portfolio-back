package com.hyk.portfolio.resource.adapter.out.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface ResourceJpaRepository extends JpaRepository<ResourceJpaEntity, Long> {

  List<ResourceJpaEntity> findAllByUrlIn(Collection<String> urls);

}
