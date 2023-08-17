--liquibase formatted sql
--changeset IvanZhuravlev:create-task-table
CREATE TABLE IF NOT EXISTS tasks
(
    id bigint NOT NULL AUTO_INCREMENT,
    title character varying(256) NOT NULL,
    description character varying(256) NOT NULL,
    priority character varying(256) NOT NULL,
    status character varying(256) NOT NULL,
    start DATETIME NOT NULL,
    finish DATETIME NOT NULL,
    owner_id bigint NOT NULL,

    CONSTRAINT tasks_pk PRIMARY KEY(id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
    );

--rollback DROP TABLE tasks;