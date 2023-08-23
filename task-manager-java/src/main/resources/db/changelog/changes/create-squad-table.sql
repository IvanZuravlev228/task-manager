--liquibase formatted sql
--changeset IvanZhuravlev:create-squad-table
CREATE TABLE IF NOT EXISTS squad
(
    id bigint NOT NULL AUTO_INCREMENT,
    title character varying(256) NOT NULL,
    owner_id bigint NOT NULL,

    CONSTRAINT squad_pk PRIMARY KEY(id),
    FOREIGN KEY (owner_id) REFERENCES users (id)
    );

--rollback DROP TABLE squad;