package DomainMocks;

import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Mission;
import missions.room.Domain.Teacher;

public class TeacherMock extends Teacher {

    public TeacherMock(String alias, String fName, String lName, String password) {
      super(alias,fName,lName,password);
    }

}
