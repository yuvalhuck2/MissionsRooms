package DataObjects.FlatDataObjects;

import java.io.Serializable;


public enum OpCode implements Serializable {

    Success,
    Final,
    Not_Exist,
    Already_Exist,
    Wrong_First_Name,
    Wrong_Last_Name,
    Wrong_Password,
    Wrong_Mail,
    Wrong_UserType,
    Wrong_Classroom,
    DB_Error,
    Dont_Match_CSV,
    Mail_Error,
    Wrong_Alias,
    Wrong_Code,
    Code_Not_Match,
    Not_Registered,
    TimeOut,
    Wrong_Key,
    System_Error,
    Null_Error, Wrong_Question,
    Wrong_Answer, Wrong_Type,
    Not_Mission, Wrong_Name,
    Wrong_Amount, Wrong_List,
    Too_Large_Amount, Too_Big_Amount,
    Type_Not_Match, Not_Exist_Template,
    Not_Exist_Student, Not_Exist_Group,
    Wrong_Bonus, Not_Exist_Classroom,
    Already_Exist_Group, Already_Exist_Class,
    Already_Exist_Student, Teacher,
    Student, IT, Supervisor,
    Wrong_File_Name, Failed_To_Read_Bytes, Wrong_File_Headers
    ,Student_Not_Exist_In_Group, Wrong_Mission_Index, Empty,
    Student_Not_Exist_In_Class, Wrong_Suggestion, Not_Exist_Room, Update_Room, Finish_Room, Finish_Missions_In_Room, IN_CHARGE, NOT_IN_CHARGE, NOT_BELONGS_TO_ROOM, Delete, Dont_Have_Permission, Not_Story, Not_Enough_Connected, STORY_IN_CHARGE,
    Trivia_Subject_Already_Exists, Invalid_Trivia_Subject, Invalid_Trivia_Question, SUBJECT_DOESNT_EXIST,
    INVALID_ROOM_ID, STUDENT_NOT_IN_CHARGE, FAILED_READ_FILE_BYTES, STORY_FINISH, Wrong_Sentence, Message_Not_Exist,
    FAILED_TO_SAVE_FILE,Teacher_Classroom_Is_Null,CONNECTED_STUDENTS,Update_Chat,NOT_BELONGS_TO_TEACHER,
     MISSION_NOT_IN_ROOM, INVALID_ANSWER, Negative_Points, Wrong_Details, Wrong_Group,
    Has_Students,
    NO_OPEN_ANSWER_FILE, FILE_SYS_ERROR, Has_Unapproved_Solutions,
    Teacher_Has_Classroom ,DELETE_USER,
    MISSION_NOT_IN_OPEN_ANS,
    APPROVED_CLOSE, APPROVED_OPEN, REJECT_CLOSE, REJECT_OPEN,
    WrongFileExt, InvalidTeacherMail, InvalidStudentMail, InvalidClassMail, InvalidClassName, ClassNotFound,
    ROOM_CLOSED, ROOM_SAVED,Not_Exist_ClassGroup, Trivia_Subject_Not_Exists, Exist_In_Mission;



}
