INSERT INTO BASE_USER (alias, password) VALUES ('admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec') ON CONFLICT DO NOTHING;
INSERT INTO IT (alias) VALUES ('admin') ON CONFLICT DO NOTHING;

-- demo part
INSERT INTO BASE_USER (alias, password) VALUES ('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('roy4', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO Classroom (class_name, points) VALUES ('0=4', 0);

INSERT INTO Class_group (group_name, group_type,classroom, points) VALUES ('A',0,'0=4', 0);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES ('niv', 'shir', 'niv'), ('roy', 'levi', 'roy4'), ('tal', 'cohen', 'tal');

INSERT INTO STUDENT (alias, class_group, points) VALUES ('niv', 'A', 0), ('roy4', 'A', 0);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES ('tal', 0,'0=4');

INSERT INTO MISSION(mission_id, points) VALUES ('mid1', 1), ('story1', 1), ('open1', 1);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES ('mid1',0), ('mid1',1), ('mid1',2), ('story1',0),('story1',1),('story1',2), ('open1',0),('open1',1),('open1',2);

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('תעלה סרטון מצחיק','open1');

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES ('שאלה','תשובה','mid1');

INSERT INTO STORY_MISSION(mission_id) VALUES ('story1');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES ('tid1',1,'תבנית כיתתית',2);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES ('tid1','mid1',0), ('tid1','story1',1), ('tid1','open1',2);

INSERT INTO ROOM(room_id,bonus,count_correct_answer ,current_mission,name,room_template_room_template_id,teacher_alias) VALUES ('rid',1,0,0,'החדר הכיתתי','tid1','tal');

INSERT INTO CLASSROOM_ROOM(room_id,participant_class_name) values ('rid','0=4');
