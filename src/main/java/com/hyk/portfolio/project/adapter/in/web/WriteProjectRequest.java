package com.hyk.portfolio.project.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.hyk.portfolio.project.application.port.in.WriteProjectCommand;
import com.hyk.portfolio.project.domain.model.Content;
import com.hyk.portfolio.project.domain.model.Description;
import com.hyk.portfolio.project.domain.model.Slug;
import com.hyk.portfolio.project.domain.model.ThemeColor;
import com.hyk.portfolio.project.domain.model.Thumbnail;
import com.hyk.portfolio.project.domain.model.Title;

record WriteProjectRequest(

    @NotBlank(message = "slug는 필수입니다")
    @Size(max = Slug.MAX_LENGTH, message = "slug는 {max}자를 초과할 수 없습니다")
    @Pattern(
        regexp = Slug.REGEX,
        flags = Pattern.Flag.CASE_INSENSITIVE,
        message = "slug 형식이 올바르지 않습니다")
    String slug,

    @NotBlank(message = "title은 필수입니다")
    @Size(max = Title.MAX_LENGTH, message = "title은 {max}자를 초과할 수 없습니다")
    String title,

    @Size(max = Thumbnail.MAX_LENGTH, message = "thumbnail은 {max}자를 초과할 수 없습니다")
    @Pattern(regexp = Thumbnail.REGEX, message = "thumbnail 형식이 올바르지 않습니다")
    String thumbnail,

    @Pattern(regexp = ThemeColor.REGEX, message = "themeColor 형식이 올바르지 않습니다")
    String themeColor,

    @Size(max = Description.MAX_LENGTH, message = "description은 {max}자를 초과할 수 없습니다")
    String description,

    @NotBlank(message = "content는 필수입니다")
    String content
) {

  WriteProjectRequest {
    thumbnail = thumbnail != null && thumbnail.isBlank() ? null : thumbnail;
    themeColor = themeColor != null && themeColor.isBlank() ? null : themeColor;
    description = description != null && description.isBlank() ? null : description;
  }

  WriteProjectCommand toCommand() {
    return new WriteProjectCommand(
        Slug.of(this.slug),
        Title.of(this.title),
        this.thumbnail != null ? Thumbnail.of(this.thumbnail) : null,
        this.themeColor != null ? ThemeColor.of(this.themeColor) : null,
        this.description != null ? Description.of(this.description) : null,
        Content.of(this.content)
    );
  }

}
