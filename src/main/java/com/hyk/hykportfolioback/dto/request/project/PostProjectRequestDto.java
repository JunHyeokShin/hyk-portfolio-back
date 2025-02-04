package com.hyk.hykportfolioback.dto.request.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostProjectRequestDto {

  @NotBlank
  @Size(max = 128)
  private String id;
  @NotBlank
  private String name;
  private String thumbnail;
  @Size(max = 7)
  private String themeColor;
  @NotBlank
  private String description;
  @NotBlank
  private String content;

}
