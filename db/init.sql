CREATE TABLE IF NOT EXISTS projects
(
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    slug        VARCHAR(128)  NOT NULL UNIQUE,
    title       VARCHAR(128)  NOT NULL,
    thumbnail   VARCHAR(2048) NULL,
    theme_color VARCHAR(7)    NULL,
    description VARCHAR(256)  NULL,
    content     LONGTEXT      NOT NULL,
    created_at  DATETIME(6)   NOT NULL,
    updated_at  DATETIME(6)   NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_projects_created_at (created_at DESC)
);

CREATE TABLE IF NOT EXISTS resources
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    filename    VARCHAR(255) NOT NULL UNIQUE,
    url         VARCHAR(255) NOT NULL UNIQUE,
    target_type VARCHAR(20)  NULL,
    target_id   BIGINT       NULL,
    status      VARCHAR(20)  NOT NULL,
    uploaded_at DATETIME(6)  NOT NULL,
    PRIMARY KEY (id)
);
