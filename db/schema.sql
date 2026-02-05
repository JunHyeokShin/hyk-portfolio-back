CREATE TABLE project
(
    id          VARCHAR(255)  NOT NULL COMMENT '프로젝트 식별 문자열',
    title       VARCHAR(255)  NOT NULL COMMENT '프로젝트 이름',
    thumbnail   VARCHAR(2048) NULL COMMENT '대표 이미지',
    theme_color VARCHAR(7)    NULL COMMENT '테마 색상값',
    description VARCHAR(255)  NULL COMMENT '프로젝트 설명',
    content     MEDIUMTEXT    NULL COMMENT '프로젝트 내용',
    view_count  INT           NOT NULL DEFAULT 0 COMMENT '프로젝트 조회 수',
    created_at  DATETIME      NOT NULL COMMENT '프로젝트 작성 날짜 및 시간',
    updated_at  DATETIME      NOT NULL COMMENT '프로젝트 수정 날짜 및 시간',
    PRIMARY KEY (id)
) COMMENT '프로젝트 테이블';
