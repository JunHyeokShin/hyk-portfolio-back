CREATE TABLE project
(
    id          VARCHAR(255)  NOT NULL COMMENT '프로젝트 ID',
    title       VARCHAR(255)  NOT NULL COMMENT '프로젝트 이름',
    thumbnail   VARCHAR(2048) NULL COMMENT '대표 이미지',
    theme_color VARCHAR(7)    NULL COMMENT '테마 색상값',
    description VARCHAR(255)  NULL COMMENT '프로젝트 설명',
    content     MEDIUMTEXT    NULL COMMENT '프로젝트 내용',
    view_count  INT           NOT NULL DEFAULT 0 COMMENT '프로젝트 조회 수',
    created_at  DATETIME(3)   NOT NULL COMMENT '프로젝트 작성 일시',
    updated_at  DATETIME(3)   NOT NULL COMMENT '프로젝트 수정 일시',
    PRIMARY KEY (id)
) COMMENT '프로젝트 테이블';

CREATE TABLE article
(
    id          VARCHAR(255)  NOT NULL COMMENT '게시글 ID',
    title       VARCHAR(255)  NOT NULL COMMENT '게시글 제목',
    thumbnail   VARCHAR(2048) NULL COMMENT '대표 이미지',
    theme_color VARCHAR(7)    NULL COMMENT '테마 색상값',
    content     MEDIUMTEXT    NULL COMMENT '게시글 내용',
    view_count  INT           NOT NULL DEFAULT 0 COMMENT '게시글 조회 수',
    created_at  DATETIME(3)   NOT NULL COMMENT '게시글 작성 일시',
    updated_at  DATETIME(3)   NOT NULL COMMENT '게시글 수정 일시',
    PRIMARY KEY (id)
) COMMENT '게시글 테이블';

CREATE TABLE resource
(
    id                VARCHAR(36)  NOT NULL COMMENT '리소스 ID',
    original_filename VARCHAR(255) NOT NULL COMMENT '원본 파일명',
    saved_filename    VARCHAR(255) NOT NULL COMMENT '저장된 파일명',
    target_type       VARCHAR(50)  NULL COMMENT '연결된 도메인 타입(PROJECT, ARTICLE)',
    target_id         VARCHAR(255) NULL COMMENT '연결된 도메인 ID',
    status            VARCHAR(20)  NOT NULL DEFAULT 'PENDING' COMMENT '상태(PENDING, SAVED)',
    created_at        DATETIME(3)  NOT NULL COMMENT '리소스 업로드 일시',
    PRIMARY KEY (id),
    UNIQUE KEY (saved_filename)
) COMMENT '리소스 테이블';
