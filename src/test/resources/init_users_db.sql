INSERT INTO USER (alias, password) VALUES
('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('roy4', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO Classroom (class_name,points) VALUES
('class',0);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'class',0);

INSERT INTO IT (alias) VALUES
('admin');

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy4'), ('tal', 'cohen', 'tal');

INSERT INTO STUDENT (alias, class_group,points) VALUES
('niv', 'A',0), ('roy4', 'A',0);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'class');

INSERT INTO MISSION(mission_id,points) VALUES
('mid1',2), ('mid2',2);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES
('mid1',0), ('mid2',2),('mid1',1),('mid1',2);

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES
('שאלה','תשובה','mid1'),('שאלה2','תשובה2','mid2');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES
('tid1',0,'תבנית אישית',0),('tid2',0,'תבנית קבוצתית',1),('tid3',0,'תבנית כיתתית',2);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES
('tid1','mid2',0),('tid2','mid1',0),('tid3','mid1',0);

INSERT INTO ROOM(room_id,bonus,count_correct_answer
,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('rid1',1,0,0,'אישי של ניב','tid1','tal'),('rid2',1,0,0,'קבוצתי של איי','tid2','tal'),('rid3',1,0,0,'כיתתי של קלאס','tid3','tal'),('rid4',1,0,0,'אישי של רוי','tid1','tal');

INSERT INTO STUDENT_ROOM(room_id,participant_alias) values
('rid1','niv'),('rid4','roy4');

INSERT INTO GROUP_ROOM(room_id,participant_group_name) values
('rid2','A');

INSERT INTO CLASSROOM_ROOM(room_id,participant_class_name) values
('rid3','class');