package com.hyk.hykportfolioback.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostPostRequestDto {

  @NotBlank
  private String title;
  private MultipartFile thumbnailFile;
  @NotBlank
  @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
  private String themeColor;
  private List<@NotBlank String> tags;
  @NotBlank
  private String content;
  private List<MultipartFile> resourceFiles;

}
