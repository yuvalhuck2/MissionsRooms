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
    Null_Error, Wrong_Question, Wrong_Answer, Wrong_Type, Not_Mission, Wrong_Name, Wrong_Amount, Wrong_List, Too_Large_Amount, Too_Big_Amount,


}
