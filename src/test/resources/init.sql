INSERT INTO base_user (alias, password) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('niv', NULL), ('roy', NULL), ('tal', NULL), ('meni', NULL);;

INSERT INTO IT (alias) VALUES
('admin');

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'niv', 'niv'), ('roy', 'roy', 'roy'), ('tal', 'tal', 'tal'),('Meni', 'Adler', 'meni');

INSERT INTO Classroom (class_name,points) VALUES
('2=4',21);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'2=4',20), ('C',2,'2=4',20);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('meni', 0,'2=4');

INSERT INTO STUDENT (alias, class_group, points) VALUES
('niv', 'C', 0), ('roy', 'C', 0), ('tal', 'C', 0);

INSERT INTO MISSION(mission_id, points) VALUES ('mid1', 1), ('story1', 1), ('open1', 1);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES ('mid1',0), ('mid1',1), ('mid1',2), ('story1',0),('story1',1),('story1',2), ('open1',0),('open1',1),('open1',2);

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('תעלה סרטון מצחיק','open1');

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES ('שאלה','תשובה','mid1');

INSERT INTO STORY_MISSION(mission_id) VALUES ('story1');