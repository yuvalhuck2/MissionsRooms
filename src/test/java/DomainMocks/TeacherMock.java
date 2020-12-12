package DomainMocks;

import DataAPI.OpCode;
import DataAPI.Response;
import Domain.Mission;
import Domain.Teacher;

public class TeacherMock extends Teacher {

    public TeacherMock(String alias, String fName, String lName, String password) {
      super(alias,fName,lName,password);
    }

    @Override
    public Response<Boolean> addMission(Mission mission) {
        return new Response<>(true, OpCode.Success);
    }
}
