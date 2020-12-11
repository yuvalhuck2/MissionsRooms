package DataAPI;

import java.io.Serializable;


public enum OpCode implements Serializable {

    Success,
    Final,
    Not_Exist, Already_Exist, Wrong_First_Name, Wrong_Last_Name, Wrong_Password, Wrong_Mail, Wrong_UserType, Wrong_Phone_Number, Wrong_Classroom, DB_Error, Dont_Match_CSV, Mail_Error, Wrong_Alias, Wrong_Code, Code_Not_Match, Not_Registered, TimeOut,


}
