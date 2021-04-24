INSERT INTO base_USER (alias, password) VALUES
('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('roy4', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO Classroom (class_name,points) VALUES
('class',0);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'class',0);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy4'), ('tal', 'cohen', 'tal');

INSERT INTO STUDENT (alias, class_group,points) VALUES
('niv', 'A',0), ('roy4', 'A',0);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'class');

INSERT INTO MISSION(mission_id,points) VALUES
('open1',2);

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES
('שאלה','open1');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES
('tid1',0,'תבנית אישית',0);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES
('tid1','open1',0);

INSERT INTO ROOM(room_id,bonus,count_correct_answer
  ,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('rid1',1,0,0,'אישי של ניב','tid1','tal');

INSERT INTO GROUP_ROOM(room_id,participant_group_name) values
('rid1','A');

INSERT INTO OPEN_ANSWER(ID, HAS_FILE, MISSION_ID, OPEN_ANSWER_TEXT, ROOM_ID) VALUES('ds', false, 'open1', 'answerrr', 'rid1')