package com.hyk.hykportfolioback.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostProjectRequestDto {

  @NotBlank
  @Size(max = 128)
  private String id;
  @NotBlank
  private String name;
  private MultipartFile thumbnailFile;
  @NotBlank
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
  private String themeColor;
  @NotBlank
  private String description;
  @NotBlank
  private String content;
  private List<MultipartFile> resourceFiles;

}
