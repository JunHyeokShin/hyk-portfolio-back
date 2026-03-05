package com.hyk.portfolio.resource.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.portfolio.resource.domain.Resource;

public interface JpaResourceRepository extends JpaRepository<Resource, UUID> {

}
