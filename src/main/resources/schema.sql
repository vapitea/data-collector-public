DROP TABLE IF EXISTS measurement;
DROP TABLE IF EXISTS parameter;
DROP TABLE IF EXISTS team_user;
DROP TABLE IF EXISTS data_source;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS user;

CREATE TABLE team (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description CLOB
);

CREATE TABLE data_source (
    id IDENTITY PRIMARY KEY,
    uuid VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    description CLOB,
    team_id BIGINT NOT NULL,
    FOREIGN KEY(team_id) REFERENCES team(id)

);

CREATE TABLE parameter (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    description CLOB,
    data_source_id BIGINT NOT NULL,
    FOREIGN KEY(data_source_id) REFERENCES data_source(id)
);

CREATE TABLE measurement (
    id IDENTITY PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    parameter_id BIGINT NOT NULL,
    value DOUBLE NULL,
    FOREIGN KEY(parameter_id) REFERENCES parameter(id)
);

CREATE TABLE user (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    dtype VARCHAR(31) NOT NULL
);

CREATE TABLE team_user (
    team_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY(team_id, user_id),
    FOREIGN KEY(team_id) REFERENCES team(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);

/* TODO Create indexes on foreign keys and commonly filtered columns  */
