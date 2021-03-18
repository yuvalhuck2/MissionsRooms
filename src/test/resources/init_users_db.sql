INSERT INTO USER (alias, password) VALUES
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
('mid1',2), ('mid2',2),('story1',2),('story2',4), ('trivia1', 5);

INSERT INTO MISSION_MISSION_TYPES(mission_mission_id,mission_types) VALUES
('mid1',0), ('mid2',2),('mid1',1),('mid1',2),
('story1',0),('story1',1),('story1',2),
('story2',0),('story2',1),('story2',2),
('trivia1', 0), ('trivia1', 1), ('trivia1', 2);

INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES
('שאלה','תשובה','mid1'),('שאלה2','תשובה2','mid2');

INSERT INTO STORY_MISSION(mission_id) VALUES
('story1'),('story2');

INSERT INTO TRIVIA_MISSION(correct_percentage, seconds_for_answer, mission_id) VALUES
(0.5, 10, 'trivia1');

INSERT INTO TRIVIA_SUBJECT(name) VALUES ( 'subject1' );

INSERT INTO TRIVIA_QUESTION(id, correct_answer, question, subject) VALUES
('0', 'ans0', 'quest0', 'subject1'), ('1', 'ans1', 'quest1', 'subject1'), ('2', 'ans2', 'quest2', 'subject1');

INSERT INTO TRIVIA_MISSION_QUESTIONS  (mission_id, id) VALUES
('trivia1', 0), ('trivia1', 1), ('trivia1', 2);

INSERT INTO TRIVIA_QUESTION_ANSWERS(trivia_question_id, answer) VALUES
('0', 'ans0'),('0', 'ans0_1'),('0', 'ans0_2'),('0', 'ans0_3'),
('1', 'ans1'),('1', 'ans1_1'),('1', 'ans1_2'),('1', 'ans1_3'),
('0', 'ans2'),('1', 'ans2_1'),('2', 'ans2_2'),('2', 'ans2_3');

INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES
('tid1',0,'תבנית אישית',0),('tid2',0,'תבנית קבוצתית',1),('tid3',0,'תבנית כיתתית',2),
('tid_story',0,'תבנית סיפור',2), ('tid_trivia',0, 'תבנית טריוויה', 2);

INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES
('tid1','mid2',0),('tid1','mid1',1),('tid2','mid1',0),('tid2','mid2',1),('tid3','mid1',0),('tid3','mid2',1)
,('tid_story','story1',0),('tid_story','story2',1), ('tid_trivia', 'trivia1', 0);

INSERT INTO ROOM(room_id,bonus,count_correct_answer
,current_mission,name,room_template_room_template_id,teacher_alias) VALUES
('rid1',1,0,0,'אישי של ניב','tid1','tal'),('rid2',1,0,0,'קבוצתי של איי','tid2','tal'),
-- ('rid3',1,0,0,'כיתתי של קלאס','tid3','tal')
-- ,('rid4',1,0,0,'אישי של רוי','tid1','tal')
('rid_story',1,0,0,'סיפור כיתתי','tid_story','tal'),
('rid_trivia', 1, 0, 0, 'טריוויה כיתתית', 'tid_trivia', 'tal');

INSERT INTO STUDENT_ROOM(room_id,participant_alias) values
('rid1','niv');--,('rid4','roy4');


INSERT INTO GROUP_ROOM(room_id,participant_group_name) values
('rid2','A');

INSERT INTO CLASSROOM_ROOM(room_id,participant_class_name) values
--('rid3','class');
('rid_story','class'), ('rid_trivia', 'class');

INSERT INTO MESSAGE(id, content, sender, date, time, dest) values
('123', 'הודעה טובה מאוד', 'shmulik', '12/12/2012','20:30','niv');

INSERT INTO SUGGESTION(id,suggestion) values
('123','הצעה טובה מאוד');