INSERT INTO USER (alias, password) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('niv', NULL), ('roy', NULL), ('tal', NULL);

INSERT INTO Classroom (class_name) VALUES
('class');

INSERT INTO Class_group (group_name, group_type,classroom) VALUES
('A',0,'class');

INSERT INTO IT (alias) VALUES
('admin');

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy'), ('tal', 'cohen', 'tal');

INSERT INTO STUDENT (alias, class_group) VALUES
('niv', 'A'), ('roy', 'A');

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'class');

