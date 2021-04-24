package missions.room.Service;

import DataAPI.*;
import missions.room.Managers.ITManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITService {

    @Qualifier("ITManager")
    @Autowired
    private ITManager itManager;

    /**
     * req 6.4 - adding new IT to the system
     * @param newIT - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(AddITData newIT){
        return itManager.addNewIT(newIT.getApiKey(),
                new RegisterDetailsData(newIT.getAlias(),
                        newIT.getPassword()));
    }

    /**
     * req 6.5- update user details
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    public Response<Boolean> updateUserDetails(String apiKey, UserProfileData profileDetails){
        return itManager.updateUserDetails(apiKey,profileDetails);
    }

    /**
     * @return get the list of of the school user
     */
    public Response<List<UserProfileData>> getAllUsersSchoolProfiles() {
        return itManager.getAllUsersSchoolProfiles();
    }

    /**
     * req 6.7 - close classroom
     * @param apiKey - authentication object
     * @param classroomName - tha identifier of the classroom need to close
     * @return if classroom closed successfully
     */
    public Response<Boolean> closeClassroom(String apiKey,String classroomName){
        return itManager.closeClassroom(apiKey, classroomName);
    }

    /**
     * req 6.9 - add user - student
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    public Response<Boolean> addStudent(String apiKey, StudentData profileDetails){
        return itManager.addStudent(apiKey,profileDetails);
    }

    /**
     * req 6.9 - add user - teacher
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    public Response<Boolean> addTeacher(String apiKey, TeacherData profileDetails){
        return itManager.addTeacher(apiKey,profileDetails);
    }
}
