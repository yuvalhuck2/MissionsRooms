INSERT INTO base_USER (alias, password) VALUES
('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('roy4', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('gal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('ron', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');


INSERT INTO IT (alias) VALUES ('gal');

INSERT INTO Classroom (class_name,points) VALUES
('2=4',21), ('1=3',2),('1=2',3);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'2=4',20), ('B',0,'1=3',20), ('A2',1,'1=3',20),('AA',0,'1=2',20),('BB',1,'1=2',20);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy4'), ('tal', 'cohen', 'tal'),('ron','keller','ron');

INSERT INTO STUDENT (alias, class_group,points) VALUES
('niv', 'A',3), ('roy4', 'A',6);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'2=4'),('ron',3,null );

INSERT INTO MISSION(mission_id,points) VALUES
('mid1',2), ('mid2',2),('story1',2),('story2',4),('open1',1),('open_mission_id',3), ('triv_mis', 1);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES
('mid1',0), ('mid2',2),('mid1',1),('mid1',2),
('story1',0),('story1',1),('story1',2),
('story2',0),('story2',1),('story2',2),
('open_mission_id',0),('open_mission_id',1),('open_mission_id',2),
('open1',0),('triv_mis',0), ('triv_mis', 1), ('triv_mis',2);

INSERT into trivia_subject(name) values
('נושא');

insert into trivia_question(id, correct_answer, question, subject) values
('trivia', 'התשובה הנכונה', 'שאלה', 'נושא'),
('trivia2', 'התשובה הנכונה2', 'שאלה2', 'נושא');

insert into trivia_question_answers(trivia_question_id, answer, index) VALUES
('trivia', 'התשובה הנכונה', 0), ('trivia', 'התשובה 3', 1), ('trivia', 'התשובה 2', 2), ('trivia', 'התשובה 1', 3),
('trivia2', 'התשובה הנכונה2', 0), ('trivia2', 'התשובה  השנייה 3', 1), ('trivia2', 'התשובה 2 השנייה', 2), ('trivia2', 'התשובה 1 השנייה', 3);

insert into trivia_mission(mission_id, pass_ratio) values
('triv_mis', 0.5);

insert into trivia_mission_questions(mission_id, id) VALUES
('triv_mis', 'trivia'),
('triv_mis', 'trivia2');

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('WHATS MY NAME','open1');
INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES
('שאלה','תשובה','mid1'),('שאלה2','תשובה2','mid2');

insert into open_answer_mission(question, mission_id) VALUES
('שאלה פתוחה כלשהי', 'open_mission_id');

INSERT INTO STORY_MISSION(mission_id) VALUES
('story1'),('story2');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES
('tid1',0,'תבנית אישית',0),('tid2',0,'תבנית קבוצתית',1),('tid3',0,'תבנית כיתתית',2),
('tid_story',0,'תבנית סיפור',2),('tid_open', 0, 'תבנית אישית', 0);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES
('tid1','mid2',0),('tid1','mid1',1),('tid2','mid1',0),('tid2','mid2',1),('tid3','mid1',0),('tid3','mid2',1)
,('tid_story','story1',0),('tid_story','story2',1),('tid_open', 'open_mission_id', 0), ('tid_open', 'mid1', 1);

INSERT INTO ROOM(room_id,bonus,count_correct_answer
,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('rid1',1,0,0,'אישי של ניב','tid1','tal'),('rid2',1,0,0,'קבוצתי של איי','tid2','tal'),
-- ('rid3',1,0,0,'כיתתי של קלאס','tid3','tal')
-- ,('rid4',1,0,0,'אישי של רוי','tid1','tal')
('rid_story',1,0,0,'סיפור כיתתי','tid_story','tal'),('rid_open',1,0,0,'open','tid_open','tal'),
('rid4',1,0,1,'אישי של רוי','tid_open','tal');


INSERT INTO STUDENT_ROOM(room_id,participant_alias) values
('rid1','niv'),('rid4','roy4');


INSERT INTO GROUP_ROOM(room_id,participant_group_name) values
('rid2','A');

INSERT INTO CLASSROOM_ROOM(room_id,participant_class_name) values
--('rid3','class');
('rid_story','2=4');

INSERT INTO MESSAGE(id, content, sender, date, time, dest) values
('123', 'הודעה טובה מאוד', 'shmulik', '12/12/2012','20:30','niv');

INSERT INTO SUGGESTION(id,suggestion) values
('123','הצעה טובה מאוד');

insert into open_answer(id, has_file, mission_id, open_answer_text, room_id) values
('open_id', false, 'open_mission_id', 'text', 'rid4');

INSERT INTO base_USER (alias, password) VALUES ('ronit', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('nivo', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),('rota','c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES('nivo', 'shir', 'nivo'), ('ronit', 'lach', 'ronit'),('rota','hananya','rota');
INSERT INTO base_USER (alias, password) VALUES
('niv', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('roy4', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('tal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('gal', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),
('ron', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');


INSERT INTO IT (alias) VALUES ('gal');

INSERT INTO Classroom (class_name,points) VALUES
('2=4',21), ('1=3',2),('1=2',3);

INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES
('A',0,'2=4',20), ('B',0,'1=3',20), ('A2',1,'1=3',20),('AA',0,'1=2',20),('BB',1,'1=2',20);

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES
('niv', 'shir', 'niv'), ('roy', 'levi', 'roy4'), ('tal', 'cohen', 'tal'),('ron','keller','ron');

INSERT INTO STUDENT (alias, class_group,points) VALUES
('niv', 'A',3), ('roy4', 'A',6);

INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES
('tal', 0,'2=4'),('ron',3,null );

INSERT INTO MISSION(mission_id,points) VALUES
('mid1',2), ('mid2',2),('story1',2),('story2',4),('open1',1),('open_mission_id',3), ('triv_mis', 1);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES
('mid1',0), ('mid2',2),('mid1',1),('mid1',2),
('story1',0),('story1',1),('story1',2),
('story2',0),('story2',1),('story2',2),
('open_mission_id',0),('open_mission_id',1),('open_mission_id',2),
('open1',0),('triv_mis',0), ('triv_mis', 1), ('triv_mis',2);

INSERT into trivia_subject(name) values
('נושא');

insert into trivia_question(id, correct_answer, question, subject) values
('trivia', 'התשובה הנכונה', 'שאלה', 'נושא'),
('trivia2', 'התשובה הנכונה2', 'שאלה2', 'נושא');

insert into trivia_question_answers(trivia_question_id, answer, index) VALUES
('trivia', 'התשובה הנכונה', 0), ('trivia', 'התשובה 3', 1), ('trivia', 'התשובה 2', 2), ('trivia', 'התשובה 1', 3),
('trivia2', 'התשובה הנכונה2', 0), ('trivia2', 'התשובה  השנייה 3', 1), ('trivia2', 'התשובה 2 השנייה', 2), ('trivia2', 'התשובה 1 השנייה', 3);

insert into trivia_mission(mission_id, pass_ratio) values
('triv_mis', 0.5);

insert into trivia_mission_questions(mission_id, id) VALUES
('triv_mis', 'trivia'),
('triv_mis', 'trivia2');

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('WHATS MY NAME','open1');
INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES
('שאלה','תשובה','mid1'),('שאלה2','תשובה2','mid2');

insert into open_answer_mission(question, mission_id) VALUES
('שאלה פתוחה כלשהי', 'open_mission_id');

INSERT INTO STORY_MISSION(mission_id) VALUES
('story1'),('story2');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES
('tid1',0,'תבנית אישית',0),('tid2',0,'תבנית קבוצתית',1),('tid3',0,'תבנית כיתתית',2),
('tid_story',0,'תבנית סיפור',2),('tid_open', 0, 'תבנית אישית', 0);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES
('tid1','mid2',0),('tid1','mid1',1),('tid2','mid1',0),('tid2','mid2',1),('tid3','mid1',0),('tid3','mid2',1)
                                                                      ,('tid_story','story1',0),('tid_story','story2',1),('tid_open', 'open_mission_id', 0), ('tid_open', 'mid1', 1);

INSERT INTO ROOM(room_id,bonus,count_correct_answer
                ,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('rid1',1,0,0,'אישי של ניב','tid1','tal'),('rid2',1,0,0,'קבוצתי של איי','tid2','tal'),
-- ('rid3',1,0,0,'כיתתי של קלאס','tid3','tal')
-- ,('rid4',1,0,0,'אישי של רוי','tid1','tal')
('rid_story',1,0,0,'סיפור כיתתי','tid_story','tal'),('rid_open',1,0,0,'open','tid_open','tal'),
('rid4',1,0,1,'אישי של רוי','tid_open','tal');


INSERT INTO STUDENT_ROOM(room_id,participant_alias) values
('rid1','niv'),('rid4','roy4');


INSERT INTO GROUP_ROOM(room_id,participant_group_name) values
('rid2','A');

INSERT INTO CLASSROOM_ROOM(room_id,participant_class_name) values
--('rid3','class');
('rid_story','2=4');

INSERT INTO MESSAGE(id, content, sender, date, time, dest) values
('123', 'הודעה טובה מאוד', 'shmulik', '12/12/2012','20:30','niv');

INSERT INTO SUGGESTION(id,suggestion) values
('123','הצעה טובה מאוד');

insert into open_answer(id, has_file, mission_id, open_answer_text, room_id) values
('open_id', false, 'open_mission_id', 'text', 'rid4');

INSERT INTO base_USER (alias, password) VALUES ('ronit', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'), ('nivo', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec'),('rota','c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES('nivo', 'shir', 'nivo'), ('ronit', 'lach', 'ronit'),('rota','hananya','rota');

INSERT INTO STUDENT (alias, class_group,points) VALUES('nivo', 'A',3), ('ronit', 'A',6), ('rota','A',0);

INSERT INTO MISSION(mission_id,points) VALUES ('m1',1), ('m2',1),('m3',2),('m4',4),('m5',1),('m6',3);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES('m1',0), ('m2',0),('m3',0),('m4',0),('m5',0),('m6',0);

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('WHATS MY NAME','m1'), ('WHATS MY NAME2','m2');

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES('שאלה','תשובה','m3'),('שאלה2','תשובה2','m4');

insert into trivia_mission(pass_ratio, mission_id) values (0.1,'m5'), (0.2, 'm6');

insert into trivia_subject(name) values ('נושא1');

insert into trivia_question(id, correct_answer, question, subject) VALUES ('1', 'a', 'שאלה', 'נושא1'), ('2', 'aa', 'שאלה2', 'נושא1');

insert into trivia_question_answers(trivia_question_id, answer, index) VALUES
('1', 'a', 0), ('1', 'b', 1), ('1', 'd', 2), ('1', 'c', 3), ('2', 'aa', 0), ('2', 'bb', 1), ('2', 'dd', 2), ('2', 'cc', 3);

insert into trivia_mission_questions(mission_id, id) values ('m5','1'),('m6', '2');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES('t',0,'תבנית פתוחה',0),('t1',0,'תבנית טריוויה',0),('t2',0,'תבנית פתרון יחיד',0);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES('t','m3',0),('t','m4',1),('t1','m5',0),('t1','m6',1),('t2','m1',1),('t2','m2',0);

INSERT INTO ROOM(room_id,bonus,count_correct_answer,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('r1',1,0,0,'פתוח','t','tal'), ('r2',1,0,0,'טריוייה','t1','tal'),('r3',1,0,0,'דטרמיניסטי','t2','tal');

insert into student_room(room_id, participant_alias)  values('r1','rota'),('r2','ronit'),('r3','nivo');
INSERT INTO STUDENT (alias, class_group,points) VALUES('nivo', 'A',3), ('ronit', 'A',6), ('rota','A',0);

INSERT INTO MISSION(mission_id,points) VALUES ('m1',1), ('m2',1),('m3',2),('m4',4),('m5',1),('m6',3);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES('m1',0), ('m2',0),('m3',0),('m4',0),('m5',0),('m6',0);

INSERT INTO OPEN_ANSWER_MISSION(question,mission_id) VALUES ('WHATS MY NAME','m1'), ('WHATS MY NAME2','m2');

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES('שאלה','תשובה','m3'),('שאלה2','תשובה2','m4');

insert into trivia_mission(pass_ratio, mission_id) values (0.1,'m5'), (0.2, 'm6');

insert into trivia_subject(name) values ('נושא1');

insert into trivia_question(id, correct_answer, question, subject) VALUES ('1', 'a', 'שאלה', 'נושא1'), ('2', 'aa', 'שאלה2', 'נושא1');

insert into trivia_question_answers(trivia_question_id, answer, index) VALUES
('1', 'a', 0), ('1', 'b', 1), ('1', 'd', 2), ('1', 'c', 3), ('2', 'aa', 0), ('2', 'bb', 1), ('2', 'dd', 2), ('2', 'cc', 3);

insert into trivia_mission_questions(mission_id, id) values ('m5','1'),('m6', '2');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES('t',0,'תבנית פתוחה',0),('t1',0,'תבנית טריוויה',0),('t2',0,'תבנית פתרון יחיד',0);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES('t','m3',0),('t','m4',1),('t1','m5',0),('t1','m6',1),('t2','m1',1),('t2','m2',0);

INSERT INTO ROOM(room_id,bonus,count_correct_answer,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('r1',1,0,0,'פתוח','t','tal'), ('r2',1,0,0,'טריוייה','t1','tal'),('r3',1,0,0,'דטרמיניסטי','t2','tal');

insert into student_room(room_id, participant_alias)  values('r1','rota'),('r2','ronit'),('r3','nivo');