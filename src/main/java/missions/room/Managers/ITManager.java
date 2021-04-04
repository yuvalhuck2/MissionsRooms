package missions.room.Managers;

import DataAPI.*;
import ExternalSystems.HashSystem;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.*;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.ITRepo;
import missions.room.Repo.SchoolUserRepo;
import missions.room.Repo.UserRepo;
import Utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class ITManager {

    protected static final Object ADD_USER_LOCK = new Object();

    @Autowired
    protected ITRepo itRepo;

    @Autowired
    protected UserRepo userRepo;

    @Autowired
    protected SchoolUserRepo schoolUserRepo;

    @Autowired
    protected ClassroomRepo classroomRepo;

    private Ram ram;

    private HashSystem hashSystem;

    public ITManager() {
        this.ram = new Ram();
        hashSystem=new HashSystem();
    }

    protected Response<IT> checkIT(String apiKey){
        String alias = ram.getAlias(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<IT> ITResponse = itRepo.findITById(alias);
        return checkITResponse(ITResponse);
    }

    private Response<IT> checkITResponse(Response<IT> ITResponse){
        if(ITResponse.getReason()!= OpCode.Success){
            log.error("connection to db lost");
            return new Response<>(null,ITResponse.getReason());
        }
        IT it = ITResponse.getValue();
        if(it == null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(it,OpCode.Success);
    }

    /**
     * req 6.4 - adding new IT to the system
     * @param apiKey - authentication object
     * @param newUser - new IT details
     * @return if user added successfully
     */
    public Response<Boolean> addNewIT(String apiKey, RegisterDetailsData newUser){
        String alias=newUser.getAlias();
        String password=newUser.getPassword();
        if(!Utils.checkString(alias)){
            log.info("add IT with empty or null alias");
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(!Utils.checkString(password)){
            log.info("add IT with empty or null password");
            return new Response<>(false,OpCode.Wrong_Password);
        }
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }
        return saveITInDB(alias, password,itResponse.getValue().getAlias());
    }

    private Response<Boolean> saveITInDB( String alias, String password,String myAlias) {
        password= hashSystem.encrypt(password);
        Response<IT> itResponse;
        synchronized (ADD_USER_LOCK){
            Response<Boolean> userResponse = userRepo.isExistsById(alias);
            if(userResponse.getReason()!= OpCode.Success){
                log.error("connection to db lost");
                return new Response<>(false,userResponse.getReason());
            }
            if(userResponse.getValue()){
                log.info("there is user with that name already");
               return new Response<>(false,OpCode.Already_Exist);
            }
            itResponse= itRepo.save(new IT(alias,password));
        }

        if(itResponse.getReason()!=OpCode.Success){
            log.error("connection to db lost");
            return new Response<>(false,itResponse.getReason());
        }
        log.info(myAlias + " added " + alias + " to be new IT successfully");
        return new Response<>(true,OpCode.Success);
    }


    /**
     * req 6.5- update user details
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    @Transactional
    public Response<Boolean> updateUserDetails(String apiKey, UserProfileData profileDetails) {
        String firstName=profileDetails.getFirstName();
        String lastName=profileDetails.getLastName();
        boolean checkFName = Utils.checkString(firstName);
        boolean checkLName = Utils.checkString(lastName);
        if(!checkFName&&!checkLName){
            log.info("add IT with empty or null first name and last name");
            return new Response<>(false,OpCode.Wrong_Details);
        }
        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        String alias=profileDetails.getAlias();
        Response<SchoolUser> schoolUserResponse = schoolUserRepo.findUserForWrite(alias);
        if(schoolUserResponse.getReason ()!= OpCode.Success){
            return new Response<>(false,schoolUserResponse.getReason());
        }
        SchoolUser schoolUser = schoolUserResponse.getValue();
        if(schoolUser == null){
            return new Response<>(false, OpCode.Not_Exist);
        }

        if(checkFName){
            schoolUser.setFirstName(firstName);
        }
        if(checkLName){
            schoolUser.setLastName(lastName);
        }

        OpCode reason = schoolUserRepo.save(schoolUser).getReason();
        return new Response<>(reason == OpCode.Success, reason);
    }

    public Response<List<UserProfileData>> getAllUsersSchoolProfiles() {
        Response<List<User>> users= schoolUserRepo.findAllSchoolUsers();
        if(users.getReason()!=OpCode.Success){
            log.error("Function getAllSchoolUsersProfiles: connection to the DB lost");
        }
        return new Response<>(users.getValue()
                .stream()
                .map((User::getProfileData))
                .collect(Collectors.toList()),
                OpCode.Success);
    }

    /**
     * req 6.9 - add user - student
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    @Transactional
    public Response<Boolean> addStudent(String apiKey, StudentData profileDetails) {
        Response<Boolean> checkDetails = checkStudentDetails(profileDetails);
        if(checkDetails.getReason()!=OpCode.Success){
            return checkDetails;
        }

        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Response<Classroom> classroomResponse = classroomRepo.findForWrite(profileDetails.getClassroom());
        if(classroomResponse.getReason()!=OpCode.Success){
            return new Response<>(false, classroomResponse.getReason());
        }

        Classroom classroom = classroomResponse.getValue();
        //classroom.addStudent(new Student(profileDetails), profileDetails.getGroupType());

        synchronized (ADD_USER_LOCK){
            Response<Boolean> isUserExist = userRepo.isExistsById(profileDetails.getAlias());
            if(isUserExist.getReason()!=OpCode.Success){
                return new Response<>(false, isUserExist.getReason());
            }
            if(isUserExist.getValue()){
                return new Response<>(false,OpCode.Already_Exist);
            }

            classroom.addStudent(new Student(profileDetails), profileDetails.getGroupType());
            OpCode saveReason = classroomRepo.save(classroom).getReason();
            return new Response<>(saveReason==OpCode.Success,saveReason);
        }
    }

    /**
     * req 6.9 - add user - teacher
     * @param apiKey - authentication object
     * @param profileDetails - the user new details.
     * @return if mail updated successfully
     */
    public Response<Boolean> addTeacher(String apiKey, TeacherData profileDetails) {
        Response<Boolean> checkDetails = checkTeacherDetails(profileDetails);
        if(checkDetails.getReason()!=OpCode.Success){
            return checkDetails;
        }

        Response<IT> itResponse = checkIT(apiKey);
        if(itResponse.getReason()!=OpCode.Success){
            log.error(itResponse.getReason().toString());
            return new Response<>(false,itResponse.getReason());
        }

        Teacher teacher;

        synchronized (ADD_USER_LOCK){
            Response<Boolean> isUserExist = userRepo.isExistsById(profileDetails.getAlias());
            if(isUserExist.getReason()!=OpCode.Success){
                return new Response<>(false, isUserExist.getReason());
            }
            if(isUserExist.getValue()){
                return new Response<>(false,OpCode.Already_Exist);
            }
            if(profileDetails.isSupervisor()){
                teacher = new Supervisor(profileDetails);
            }
            else{
                teacher = new Teacher(profileDetails);
            }
            OpCode saveReason = schoolUserRepo.save(teacher).getReason();
            return new Response<>(saveReason==OpCode.Success,saveReason);
        }
    }

    private Response<Boolean> checkStudentDetails(StudentData profileDetails) {
        if(!Utils.checkString(profileDetails.getClassroom())){
            return new Response<>(false,OpCode.Wrong_Classroom);
        }
        if(profileDetails.getGroupType()==null||profileDetails.getGroupType()==GroupType.BOTH){
            return new Response<>(false,OpCode.Wrong_Group);
        }
        return checkTeacherDetails(new TeacherData(profileDetails));
    }

    private Response<Boolean> checkTeacherDetails(TeacherData teacherData) {
        if(!Utils.checkString(teacherData.getAlias())){
            return new Response<>(false,OpCode.Wrong_Alias);
        }
        if(!Utils.checkString(teacherData.getFirstName())){
            return new Response<>(false,OpCode.Wrong_First_Name);
        }
        if(!Utils.checkString(teacherData.getLastName())){
            return new Response<>(false,OpCode.Wrong_Last_Name);
        }
        return new Response<>(true,OpCode.Success);
    }
}
