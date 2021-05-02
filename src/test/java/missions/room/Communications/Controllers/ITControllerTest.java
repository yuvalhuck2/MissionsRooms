package missions.room.Communications.Controllers;

import Data.Data;
import DataObjects.APIObjects.AddITData;
import DataObjects.FlatDataObjects.OpCode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Classroom;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

class ITControllerTest extends ControllerTest {

    private static final AddITData itDetailsData = new AddITData("apiKey", "alias", "name");

    @Test
    void testAddNewIT() {
        String body = gson.toJson(itDetailsData);
        testControllerWithBody(ADD_NEW_IT, body, Wrong_Key, false);
    }

    @Test
    void getAllUsersSchoolProfiles() {
        testControllerNoBody(GET_ALL_SCHOOL_USERS, OpCode.Success,new ArrayList<>());
    }

    @Test
    void updateUserDetails() {
        String body = gson.toJson(dataGenerator.getProfileData(Data.VALID));
        testControllerWithBody(UPDATE_DETAILS, body, Wrong_Key, false);
    }

    @Test
    void addNewStudent() {
        String body = gson.toJson(dataGenerator.getProfileData(Data.VALID));
        testControllerWithBody(ADD_STUDENT, body, Wrong_Classroom, false);
    }

    @Test
    void addNewTeacher() {
        String body = gson.toJson(dataGenerator.getProfileData(Data.VALID));
        testControllerWithBody(ADD_TEACHER, body, Wrong_Key, false);
    }

    @Test
    void closeClassroom() {
        String body = gson.toJson(dataGenerator.getClassroomData(Data.Valid_Classroom));
        testControllerWithBody(CLOSE_CLASSROOM, body, Wrong_Key, false);
    }

    @Test
    void deleteUser() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(DELETE_USER, body, Wrong_Key, false);
    }

    @Test
    void deleteSeniorStudents() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(DELETE_SENIORS, body, Wrong_Key, null);
    }

    //TODO transfer teacher and transfer student
}