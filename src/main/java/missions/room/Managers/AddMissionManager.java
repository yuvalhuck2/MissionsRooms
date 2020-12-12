package missions.room.Managers;

import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import Domain.Mission;
import Domain.Ram;
import Domain.Teacher;
import ExternalSystems.UniqueStringGenerator;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddMissionManager {

    private final Ram ram;
    private final Gson gson;
    private final String missionName="mis";

    @Autowired
    private TeacherRepo teacherRepo;

    public AddMissionManager() {
        ram=new Ram();
        GsonBuilder builderMission = new GsonBuilder();
        builderMission.registerTypeAdapter(Mission.class, new InterfaceAdapter());
        gson = builderMission.create();
    }

    public AddMissionManager(Ram ram, TeacherCrudRepository teacherCrudRepository) {
        this.ram = ram;
        teacherRepo=new TeacherRepo(teacherCrudRepository);
        GsonBuilder builderMission = new GsonBuilder();
        builderMission.registerTypeAdapter(Mission.class, new InterfaceAdapter());
        gson = builderMission.create();
    }

    /**
     * req 4.5 - add mission
     * @param apiKey - authentication key
     * @param missionData - details of the mission
     * @return if the mission was added successfully
     */
    public Response<Boolean> addMission(String apiKey, String missionData){
        String alias=ram.getApi(apiKey);
        if(alias==null){
            return new Response<>(false, OpCode.Wrong_Key);
        }

        Response<Mission> missionResponse=getMissionFromData(missionData);
        if(missionResponse.getReason()!=OpCode.Success){
            return new Response<>(false,missionResponse.getReason());
        }
        Response<Teacher> teacherResponse=teacherRepo.findTeacherById(alias);
        if(teacherResponse.getReason()!=OpCode.Success){//teacher repo not mock
            return new Response<>(false,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        if(teacher==null){//teacher repo not mock
            return new Response<>(false,OpCode.Not_Exist);
        }
        return teacher.addMission(missionResponse.getValue());//teacher not mock

    }

    private Response<Mission> getMissionFromData(String missionData) {
        try {
            Mission mission = gson.fromJson(missionData, Mission.class);
            if(mission==null){
                return new Response<>(null,OpCode.Null_Error);
            }
            OpCode opCode=mission.validate();
            if(opCode!=OpCode.Success){//mission not mock
                return new Response<>(null,opCode);
            }
            mission.setMissionId(UniqueStringGenerator.getTimeNameCode(missionName));
            return new Response<>(mission,OpCode.Success);
        }
        catch (Exception ignored){
            //TODO write to logger
        }
        return new Response<>(null,OpCode.Not_Mission);
    }
}
