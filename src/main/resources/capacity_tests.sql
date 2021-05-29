do $$
    declare
        n integer:= 0;
        counter integer:= 0;
        m integer:=0;
    begin
        INSERT INTO BASE_USER (alias, password) VALUES ('tal','tal');
        INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES ('tal', 'tal','tal');
        INSERT INTO MISSION(mission_id,points) VALUES ('mis',0);
        INSERT INTO KNOWN_ANSWER_MISSION(question,real_answer,mission_id) VALUES('a','a','mis');
        INSERT INTO ROOM_TEMPLATE(room_template_id,minimal_missions_to_pass,name,type) VALUES('tid',0,'a',0);
        INSERT INTO MISSION_TEMPLATES(room_template_id,mission_id,index) VALUES('tid','mis',0);
        INSERT INTO Classroom (class_name,points) VALUES('2=4',0);
        INSERT INTO Class_group (group_name, group_type,classroom,points) VALUES('A',0,'2=4',0);
        INSERT INTO TEACHER (alias,group_type, classroom_class_name) VALUES ('tal',0, '2=4');
        loop
            exit when counter = 1050 ;
            counter := counter + 1 ;
            INSERT INTO BASE_USER (alias, password) VALUES (counter,'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');
            INSERT INTO SCHOOL_USER (first_name, last_name, alias) VALUES ('name', 'lname',counter);
            INSERT INTO STUDENT (alias, class_group,points) VALUES(counter, 'A',0);
        end loop;
        loop
            exit when n = 1000 ;
            n := n + 1 ;
            INSERT INTO ROOM(room_id,bonus,count_correct_answer,current_mission,name,room_template_room_template_id,teacher_alias) VALUES(n,1,0,0,'p','tid','tal');
            INSERT INTO STUDENT_ROOM(room_id,participant_alias) values(n, n);
        end loop;
        loop
            exit when m = 4000 ;
            m := m + 1 ;
            INSERT INTO MISSION(mission_id,points) VALUES (n,0);
        end loop;
    end; $$