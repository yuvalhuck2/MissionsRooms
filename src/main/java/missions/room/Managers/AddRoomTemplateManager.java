package missions.room.Managers;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomTemplateDetailsData;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Teacher;
import missions.room.Repo.MissionRepo;
import missions.room.Repo.RoomTemplateRepo;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AddRoomTemplateManager {

    @Autowired
    private RoomTemplateRepo roomTemplateRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private MissionRepo missionRepo;

    private final Ram ram;

    public AddRoomTemplateManager() {
        this.ram = new Ram();
    }

    public AddRoomTemplateManager(Ram ram, TeacherCrudRepository teacherCrudRepository, MissionCrudRepository missionCrudRepository, RoomTemplateCrudRepository roomTemplateCrudRepository) {
        this.ram=ram;
        this.teacherRepo=new TeacherRepo(teacherCrudRepository);
        this.missionRepo=new MissionRepo(missionCrudRepository);
        this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);
    }

    public Response<Boolean> createRoomTemplate(String apiKey, RoomTemplateDetailsData details) {
        String alias=ram.getApi(apiKey);
        if(alias==null){
            return new Response<>(false, OpCode.Wrong_Key);
        }
        Response<RoomTemplate> templateResponse= validateDetails(details);
        if(templateResponse.getReason()!=OpCode.Success){
            return new Response<>(false,templateResponse.getReason());
        }
        Response<Teacher> teacherResponse=teacherRepo.findTeacherById(alias);
        if(teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(false,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        if(teacher==null){//teacher repo not mock
            return new Response<>(false,OpCode.Not_Exist);
        }
        return saveRoomTemplate(templateResponse.getValue());
    }

    private Response<Boolean> saveRoomTemplate(RoomTemplate roomTemplate) {
        Response<RoomTemplate> templateResponse=roomTemplateRepo.save(roomTemplate);
        if(templateResponse.getReason()!= OpCode.Success){
            return new Response<>(false,templateResponse.getReason());
        }

        return new Response<>(true,OpCode.Success);
    }

    private Response<RoomTemplate> validateDetails(RoomTemplateDetailsData details) {
        if(!Utils.checkString(details.getName())){
            return new Response<>(null,OpCode.Wrong_Name);
        }

        if(details.getMinimalMissionsToPass()<0){
            return new Response<>(null,OpCode.Wrong_Amount);
        }

        if(details.getType()==null){
            return new Response<>(null,OpCode.Wrong_Type);
        }

        Response<HashMap<String,Mission>> missionsResponse=getMissions(details.getMissions());
        if(missionsResponse.getReason()!=OpCode.Success){
            return new Response<>(null,missionsResponse.getReason());
        }
        //amount is bigger then the missions in the template
        if(details.getMinimalMissionsToPass()>details.getMissions().size()){
            return new Response<>(null,OpCode.Too_Big_Amount);
        }

        details.setId(UniqueStringGenerator.getUniqueCode("rt"));
        return new Response<>(new RoomTemplate(details,missionsResponse.getValue()),OpCode.Success);
    }

    private Response<HashMap<String,Mission>> getMissions(List<String> missions) {
        HashMap<String,Mission> missionMap=new HashMap<>();
        if(missions==null||missions.isEmpty()){
            return new Response<>(null,OpCode.Wrong_List);
        }
        for(String missionId: missions){
            Response<Mission> missionResponse=missionRepo.findMissionById(missionId);
            if(missionResponse.getReason()!= OpCode.Success){
                return new Response<>(null,missionResponse.getReason());
            }
            Mission mission=missionResponse.getValue();
            if(mission==null){
                return new Response<>(null,OpCode.Not_Mission);
            }
            missionMap.put(missionId,mission);
        }
        return new Response<>(missionMap,OpCode.Success);

    }
}
