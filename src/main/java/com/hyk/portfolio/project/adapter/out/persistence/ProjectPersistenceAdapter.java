package com.hyk.portfolio.project.adapter.out.persistence;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;
import com.hyk.portfolio.project.application.port.out.SaveProjectPort;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@Component
class ProjectPersistenceAdapter implements SaveProjectPort, LoadProjectPort {

  private final ProjectJpaRepository jpaRepository;

  @Override
  // update 분기가 dirty checking에 의존한다. 바깥 트랜잭션이 없으면 수정이 조용히 유실된다
  @Transactional(propagation = Propagation.MANDATORY)
  public Project save(Project project) {
    try {
      if (project.getId() == null) {
        // flush를 강제해야 제약 위반이 여기서 잡힌다. 지연되면 커밋 시점에 터져 catch를 벗어난다
        return ProjectMapper.toDomain(
            this.jpaRepository.saveAndFlush(ProjectMapper.toEntity(project)));
      }
      ProjectJpaEntity entity = this.jpaRepository.findById(project.getId())
          .orElseThrow(() -> new IllegalStateException(
              "저장하려는 프로젝트가 존재하지 않습니다: " + project.getId()));
      ProjectMapper.updateEntity(entity, project);
      this.jpaRepository.flush();
      return ProjectMapper.toDomain(entity);
    }
    // projects의 유일한 unique 제약이 slug이므로 제약 위반은 slug 중복으로 단정한다
    // 서비스의 선체크만으로는 동시 요청에서 500이 나간다
    catch (DataIntegrityViolationException e) {
      throw new BusinessException(ProjectErrorCode.SLUG_DUPLICATED, e);
    }
  }

  @Override
  public Optional<Project> findBySlug(Slug slug) {
    return this.jpaRepository.findBySlug(slug.value())
        .map(ProjectMapper::toDomain);
  }

  @Override
  public boolean existsBySlug(Slug slug) {
    return this.jpaRepository.existsBySlug(slug.value());
  }

}
