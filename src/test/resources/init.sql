DROP TABLE IF EXISTS IT;
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
    alias VARCHAR(250) NOT NULL,
    password VARCHAR(250),
    PRIMARY KEY (alias)
);

CREATE TABLE IT (
    alias VARCHAR(250) NOT NULL,
    PRIMARY KEY (alias)
);



INSERT INTO USER (alias, password) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('niv', NULL), ('roy', NULL), ('tal', NULL);

INSERT INTO IT (alias) VALUES
('admin');

//TODO: DELETE! ONLY FOR TESTS
DROP TABLE IF EXISTS STUDENT;
DROP TABLE IF EXISTS SCHOOL_USER;
CREATE TABLE STUDENT (
    alias VARCHAR(250) NOT NULL,
    classgroup VARCHAR(250) NOT NULL,
    PRIMARY KEY (alias)
);

INSERT INTO STUDENT (alias, classgroup) VALUES
('niv', 'A'), ('roy', 'A'), ('tal', 'A');

CREATE TABLE SCHOOL_USER (
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    alias VARCHAR(250) NOT NULL,
    PRIMARY KEY (alias)
);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'niv', 'niv'), ('roy', 'roy', 'roy'), ('tal', 'tal', 'tal');