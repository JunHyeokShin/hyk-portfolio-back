package com.hyk.portfolio.project.application.port.in;

import com.hyk.portfolio.project.domain.model.Content;
import com.hyk.portfolio.project.domain.model.Description;
import com.hyk.portfolio.project.domain.model.Slug;
import com.hyk.portfolio.project.domain.model.ThemeColor;
import com.hyk.portfolio.project.domain.model.Thumbnail;
import com.hyk.portfolio.project.domain.model.Title;

public record WriteProjectCommand(
    Slug slug,
    Title title,
    Thumbnail thumbnail,
    ThemeColor themeColor,
    Description description,
    Content content
) {

  public WriteProjectCommand {
    if (slug == null) {
      throw new IllegalArgumentException("slug는 필수입니다");
    }
    if (title == null) {
      throw new IllegalArgumentException("title은 필수입니다");
    }
    if (content == null) {
      throw new IllegalArgumentException("content는 필수입니다");
    }
  }

}
