package DataAPI;

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
    Student_Not_Exist_In_Class, Wrong_Suggestion, Not_Exist_Room, Update_Room, Finish_Room, Finish_Missions_In_Room, IN_CHARGE, NOT_IN_CHARGE, NOT_BELONGS_TO_ROOM, Delete, Dont_Have_Permission;



}
