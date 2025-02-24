package com.hyk.hykportfolioback.entity;

import com.hyk.hykportfolioback.entity.primaryKey.PostTagPk;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post_tag")
@Table(name = "post_tag")
@IdClass(PostTagPk.class)
public class PostTagEntity {

  @Id
  private int postId;
  @Id
  private int tagId;

}
