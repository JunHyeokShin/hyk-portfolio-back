package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import com.hyk.hykportfolioback.repository.ProjectRepository;
import com.hyk.hykportfolioback.repository.resultSet.GetProjectListResultSet;
import com.hyk.hykportfolioback.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplement implements ProjectService {

  private final ProjectRepository projectRepository;

  @Override
  public ResponseEntity<? super GetProjectListResponseDto> getProjectList() {
    List<GetProjectListResultSet> resultSets = new ArrayList<>();

    try {
      resultSets = projectRepository.findProjectListBy();
    } catch (Exception exception) {
      exception.printStackTrace();
      ResponseDto.databaseError();
    }

    return GetProjectListResponseDto.success(resultSets);
  }

  @Override
  public ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto) {
    try {
      String id = dto.getId();
      boolean existsId = projectRepository.existsById(id);
      if (existsId) return PostProjectResponseDto.duplicateId();

      ProjectEntity projectEntity = new ProjectEntity(dto);
      projectRepository.save(projectEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostProjectResponseDto.success();
  }

}
