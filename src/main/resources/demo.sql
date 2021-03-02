INSERT INTO USER (alias, password) VALUES
('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('roy', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO Classroom (class_name,points) VALUES
('class',0);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'class',0);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy'), ('tal', 'cohen', 'tal');

INSERT INTO STUDENT (alias, class_group,points) VALUES
('niv', 'A',0), ('roy', 'A',0);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'class');