CREATE TABLE users
(
    id         BIGINT,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    version    INT          NOT NULL,
    username   VARCHAR(32)  NOT NULL,
    email      VARCHAR(128) NOT NULL,
    password   VARCHAR(68),
    first_name VARCHAR(48)  NOT NULL,
    last_name  VARCHAR(48)  NOT NULL,
    country    VARCHAR(48)  NOT NULL,
    city       VARCHAR(48)  NOT NULL,
    bio        VARCHAR(256),
    enabled    SMALLINT     NOT NULL DEFAULT 1,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT uk_email UNIQUE (email)
);

CREATE TABLE authorities
(
    email     VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities FOREIGN KEY (email) REFERENCES users (email),
    CONSTRAINT uk_email_authority UNIQUE (email, authority)
);

CREATE TABLE conference
(
    id         BIGINT,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    version    INT          NOT NULL,
    title      VARCHAR(256) NOT NULL,
    country    VARCHAR(48)  NOT NULL,
    city       VARCHAR(48)  NOT NULL,
    CONSTRAINT pk_conference PRIMARY KEY (id),
    CONSTRAINT uk_title UNIQUE (title)
);

CREATE TABLE paper
(
    id               BIGINT,
    created_on       TIMESTAMP WITH TIME ZONE,
    updated_on       TIMESTAMP WITH TIME ZONE,
    version          INT           NOT NULL,
    title            VARCHAR(250)  NOT NULL,
    summary          VARCHAR(1024) NOT NULL,
    is_approved      BOOLEAN       NOT NULL,
    publication_date DATE          NOT NULL,
    download_count   INT           NOT NULL,
    total_rating     BIGINT        NOT NULL,
    total_rated      BIGINT        NOT NULL,
    file_name        VARCHAR(255),
    file             BYTEA,
    conference_id    BIGINT,
    CONSTRAINT pk_paper PRIMARY KEY (id),
    CONSTRAINT fk_paper_conference FOREIGN KEY (conference_id) REFERENCES conference (id)
);

CREATE TABLE saved
(
    user_id  BIGINT,
    paper_id BIGINT,
    CONSTRAINT pk_saved PRIMARY KEY (user_id, paper_id),
    CONSTRAINT fk_saved_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_saved_paper FOREIGN KEY (paper_id) REFERENCES paper (id)
);

CREATE TABLE follow
(
    following_id BIGINT,
    follower_id  BIGINT,
    CONSTRAINT pk_follow PRIMARY KEY (following_id, follower_id),
    CONSTRAINT fk_following FOREIGN KEY (following_id) REFERENCES users (id),
    CONSTRAINT fk_follower FOREIGN KEY (follower_id) REFERENCES users (id)
);

CREATE TABLE tag
(
    id         BIGINT,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    version    INT         NOT NULL,
    name       VARCHAR(32) NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id),
    CONSTRAINT uk_name UNIQUE (name)
);

CREATE TABLE paper_user
(
    paper_id BIGINT,
    user_id  BIGINT,
    CONSTRAINT pk_paper_user PRIMARY KEY (paper_id, user_id),
    CONSTRAINT fk_paper_user_paper FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT fk_paper_user_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE paper_tag
(
    paper_id BIGINT,
    tag_id   BIGINT,
    CONSTRAINT pk_paper_tag PRIMARY KEY (paper_id, tag_id),
    CONSTRAINT fk_paper_tag_paper FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT fk_paper_tag_tag FOREIGN KEY (tag_id) REFERENCES tag (id)
);

CREATE TABLE rating
(
    id         BIGINT,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    version    INT    NOT NULL,
    rating     BIGINT NOT NULL,
    paper_id   BIGINT,
    user_id    BIGINT,
    CONSTRAINT pk_rating PRIMARY KEY (id),
    CONSTRAINT fk_rating_paper FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT fk_rating_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE notification
(
    id          BIGINT,
    created_on  TIMESTAMP WITH TIME ZONE,
    updated_on  TIMESTAMP WITH TIME ZONE,
    version     INT          NOT NULL,
    receiver_id BIGINT,
    message     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id),
    CONSTRAINT fk_notification_receiver FOREIGN KEY (receiver_id) REFERENCES users (id)
);

CREATE TABLE comment
(
    id         BIGINT,
    created_on TIMESTAMP WITH TIME ZONE,
    updated_on TIMESTAMP WITH TIME ZONE,
    version    INT          NOT NULL,
    text       VARCHAR(512) NOT NULL,
    paper_id   BIGINT       NOT NULL,
    user_id    BIGINT       NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_comment_paper FOREIGN KEY (paper_id) REFERENCES paper (id),
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE comment_likes
(
    comment_id BIGINT NOT NULL,
    user_id    BIGINT NOT NULL,
    CONSTRAINT pk_comment_likes PRIMARY KEY (comment_id, user_id),
    CONSTRAINT fk_comment_likes_comment FOREIGN KEY (comment_id) REFERENCES comment (id),
    CONSTRAINT fk_comment_likes_user FOREIGN KEY (user_id) REFERENCES users (id)
);