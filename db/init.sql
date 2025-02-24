CREATE TABLE project
(
    id            VARCHAR(128) NOT NULL COMMENT '프로젝트 식별 문자열',
    name          TEXT         NOT NULL COMMENT '프로젝트 이름',
    thumbnail     TEXT         NULL COMMENT '썸네일',
    theme_color   VARCHAR(7)   NOT NULL COMMENT '테마 색상값',
    description   TEXT         NOT NULL COMMENT '프로젝트 설명',
    content       TEXT         NOT NULL COMMENT '프로젝트 내용',
    comment_count INT          NOT NULL DEFAULT 0 COMMENT '프로젝트 댓글 수',
    view_count    INT          NOT NULL DEFAULT 0 COMMENT '프로젝트 조회 수',
    created_at    DATETIME     NOT NULL COMMENT '프로젝트 작성 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '프로젝트 테이블';

CREATE TABLE post
(
    id            INT        NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
    title         TEXT       NOT NULL COMMENT '게시물 제목',
    thumbnail     TEXT       NULL COMMENT '썸네일',
    theme_color   VARCHAR(7) NOT NULL COMMENT '테마 색상값',
    content       TEXT       NOT NULL COMMENT '게시물 내용',
    comment_count INT        NOT NULL DEFAULT 0 COMMENT '게시물 댓글 수',
    view_count    INT        NOT NULL DEFAULT 0 COMMENT '게시물 조회 수',
    created_at    DATETIME   NOT NULL COMMENT '게시물 작성 날짜 및 시간',
    updated_at    DATETIME   NOT NULL COMMENT '게시물 수정 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '게시물 테이블';

CREATE TABLE project_comment
(
    id         INT          NOT NULL AUTO_INCREMENT COMMENT '댓글 번호',
    nickname   VARCHAR(20)  NOT NULL COMMENT '닉네임',
    content    TEXT         NOT NULL COMMENT '내용',
    created_at DATETIME     NOT NULL COMMENT '댓글 작성 날짜 및 시간',
    project_id VARCHAR(128) NOT NULL COMMENT '프로젝트 식별 문자열',
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE
) COMMENT '프로젝트 댓글 테이블';

CREATE TABLE post_comment
(
    id         INT         NOT NULL AUTO_INCREMENT COMMENT '댓글 번호',
    nickname   VARCHAR(20) NOT NULL COMMENT '닉네임',
    content    TEXT        NOT NULL COMMENT '내용',
    created_at DATETIME    NOT NULL COMMENT '댓글 작성 날짜 및 시간',
    post_id    INT         NOT NULL COMMENT '게시물 번호',
    PRIMARY KEY (id),
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
) COMMENT '게시물 댓글 테이블';

CREATE TABLE tag
(
    id         INT         NOT NULL AUTO_INCREMENT COMMENT '태그 번호',
    name       VARCHAR(20) NOT NULL UNIQUE COMMENT '태그 이름',
    created_at DATETIME    NOT NULL COMMENT '태그 생성 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '태그 테이블';

CREATE TABLE post_tag
(
    post_id INT NOT NULL COMMENT '게시물 번호',
    tag_id  INT NOT NULL COMMENT '태그 번호',
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) COMMENT '게시물 태그 N:M 관계 테이블';

CREATE VIEW post_with_tags AS
SELECT p.id,
       p.title,
       p.thumbnail,
       p.theme_color,
       p.content,
       IF(COUNT(t.name) = 0, JSON_ARRAY(), JSON_ARRAYAGG(t.name)) AS tags,
       p.comment_count,
       p.view_count,
       p.created_at,
       p.updated_at
FROM post AS p
         LEFT JOIN post_tag AS pt ON p.id = pt.post_id
         LEFT JOIN tag AS t ON pt.tag_id = t.id
GROUP BY p.id,
         p.title,
         p.thumbnail,
         p.theme_color,
         p.content,
         p.comment_count,
         p.view_count,
         p.created_at,
         p.updated_at;
