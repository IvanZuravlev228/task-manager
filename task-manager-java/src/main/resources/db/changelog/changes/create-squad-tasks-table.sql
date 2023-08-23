--liquibase formatted sql
--changeset IvanZhuravlev:create-squad-tasks-table
CREATE TABLE IF NOT EXISTS squad_tasks
(
    squad_id bigint NOT NULL,
    tasks_id bigint NOT NULL,

    FOREIGN KEY (squad_id) REFERENCES squad (id),
    FOREIGN KEY (tasks_id) REFERENCES tasks (id)
    );

--rollback DROP TABLE squad_tasks;