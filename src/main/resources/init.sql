DROP TABLE IF EXISTS IT;

DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
    alias VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    PRIMARY KEY (alias)
);

CREATE TABLE IT (
    alias VARCHAR(250) NOT NULL,
    PRIMARY KEY (alias)
);



INSERT INTO USER (alias, password) VALUES
('admin', 'admin');

INSERT INTO IT (alias) VALUES
('admin');