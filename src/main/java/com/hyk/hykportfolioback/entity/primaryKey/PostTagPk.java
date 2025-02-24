package com.hyk.hykportfolioback.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostTagPk implements Serializable {

  @Column(name = "post_id")
  private int postId;
  @Column(name = "tag_id")
  private int tagId;

}
