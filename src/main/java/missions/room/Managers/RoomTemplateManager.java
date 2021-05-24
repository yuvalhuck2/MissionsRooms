package missions.room.Managers;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataObjects.APIObjects.RoomTemplateDetailsData;
import DataObjects.APIObjects.RoomTemplateForSearch;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.RoomType;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import missions.room.Domain.*;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.missions.Mission;
import missions.room.Repo.MissionRepo;
import missions.room.Repo.RoomTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTemplateManager extends TeacherManager{

    @Autowired
    private RoomTemplateRepo roomTemplateRepo;

    @Autowired
    private MissionRepo missionRepo;

    public RoomTemplateManager() {
        super();
    }

    public RoomTemplateManager(Ram ram, TeacherCrudRepository teacherCrudRepository, MissionCrudRepository missionCrudRepository, RoomTemplateCrudRepository roomTemplateCrudRepository) {
        super(ram,teacherCrudRepository);
        this.missionRepo=new MissionRepo(missionCrudRepository);
        this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);
    }

    public Response<Boolean> createRoomTemplate(String apiKey, RoomTemplateDetailsData details) {
        Response<Teacher> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!= OpCode.Success){
            return new Response<>(false,checkTeacher.getReason());
        }
        Response<RoomTemplate> templateResponse= validateDetails(details);
        if(templateResponse.getReason()!=OpCode.Success){
            return new Response<>(false,templateResponse.getReason());
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

        Response<List<Mission>> missionsResponse=getMissions(details.getMissions(),details.getType());
        if(missionsResponse.getReason()!=OpCode.Success){
            return new Response<>(null,missionsResponse.getReason());
        }
        //amount is bigger then the missions in the template
        if(details.getMinimalMissionsToPass()>details.getMissions().size()){
            return new Response<>(null,OpCode.Too_Big_Amount);
        }

        details.setId(UniqueStringGenerator.getTimeNameCode("rt"));
        return new Response<>(new RoomTemplate(details,missionsResponse.getValue()),OpCode.Success);
    }

    private Response<List<Mission>> getMissions(List<String> missions, RoomType roomType) {
        List<Mission> missionList=new ArrayList<>();
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
            if(!mission.containType(roomType)){
                return new Response<>(null,OpCode.Type_Not_Match);
            }
            missionList.add(mission);
        }
        return new Response<>(missionList,OpCode.Success);

    }

    /**
     * req 4.14 - look for room templates
     * @param apiKey - authentication object
     * @return list of filtered room templates
     */
    public Response<List<RoomTemplateForSearch>> searchRoomTemplates(String apiKey){
        Response<Teacher> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!=OpCode.Success){
            return new Response<>(null,checkTeacher.getReason());
        }
        Response<List<RoomTemplate>> templateResponse=roomTemplateRepo.findAllTemplates();
        if(templateResponse.getReason()!=OpCode.Success){
            return new Response<>(null,templateResponse.getReason());
        }
        List<RoomTemplateForSearch> roomTemplateForSearches=templateResponse.getValue().
                parallelStream().
                map(RoomTemplate::getRoomTemplateForSearch)
                .collect(Collectors.toList());
        return new Response<>(roomTemplateForSearches,OpCode.Success);
    }

}
