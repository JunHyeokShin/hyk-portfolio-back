CREATE TABLE project
(
    id          VARCHAR(255)  NOT NULL COMMENT '프로젝트 식별 문자열',
    title       VARCHAR(255)  NOT NULL COMMENT '프로젝트 이름',
    thumbnail   VARCHAR(2048) NULL COMMENT '대표 이미지',
    theme_color VARCHAR(7)    NULL COMMENT '테마 색상값',
    description VARCHAR(255)  NULL COMMENT '프로젝트 설명',
    content     MEDIUMTEXT    NULL COMMENT '프로젝트 내용',
    view_count  INT           NOT NULL DEFAULT 0 COMMENT '프로젝트 조회 수',
    created_at  DATETIME(3)   NOT NULL COMMENT '프로젝트 작성 날짜 및 시간',
    updated_at  DATETIME(3)   NOT NULL COMMENT '프로젝트 수정 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '프로젝트 테이블';

CREATE TABLE post
(
    id          VARCHAR(255)  NOT NULL COMMENT '게시글 식별 문자열',
    title       VARCHAR(255)  NOT NULL COMMENT '게시글 이름',
    thumbnail   VARCHAR(2048) NULL COMMENT '대표 이미지',
    theme_color VARCHAR(7)    NULL COMMENT '테마 색상값',
    content     MEDIUMTEXT    NULL COMMENT '게시글 내용',
    view_count  INT           NOT NULL DEFAULT 0 COMMENT '게시글 조회 수',
    created_at  DATETIME(3)   NOT NULL COMMENT '게시글 작성 날짜 및 시간',
    updated_at  DATETIME(3)   NOT NULL COMMENT '게시글 수정 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '게시글 테이블';

CREATE TABLE resource
(
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '리소스 식별자',
    file       VARCHAR(255) NOT NULL COMMENT '리소스 파일 이름',
    project_id VARCHAR(255) NULL COMMENT '프로젝트 식별 문자열',
    post_id    VARCHAR(255) NULL COMMENT '게시글 식별 문자열',
    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
) COMMENT '리소스 테이블';
