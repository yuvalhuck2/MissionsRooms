package missions.room.Managers;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.Teacher;
import missions.room.Domain.TriviaQuestion;
import missions.room.Domain.missions.*;
import missions.room.Repo.MissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MissionManager extends TeacherManager {

    private final Gson gson;
    private final String missionName="mis";

    @Autowired
    private MissionRepo missionRepo;

    public MissionManager() {
        super();
        GsonBuilder builderMission = new GsonBuilder();
        builderMission.registerTypeAdapter(Mission.class, new InterfaceAdapter());
        gson = builderMission.create();
    }

    public MissionManager(Ram ram, TeacherCrudRepository teacherCrudRepository, MissionCrudRepository missionCrudRepository) {
        super(ram,teacherCrudRepository);
        missionRepo=new MissionRepo(missionCrudRepository);
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
        Response<Teacher> teacherResponse=checkTeacher(apiKey);
        if(teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(false,teacherResponse.getReason());
        }
        Response<Mission> missionResponse=getMissionFromData(missionData);
        if(missionResponse.getReason()!=OpCode.Success){
            return new Response<>(false,missionResponse.getReason());
        }

        return addMission(missionResponse.getValue());

    }

    private Response<Mission> getMissionFromData(String missionData) {
        try {
            Mission mission = gson.fromJson(missionData, Mission.class);
            if(mission==null){
                return new Response<>(null,OpCode.Null_Error);
            }
            OpCode opCode=mission.validate();
            if(opCode!=OpCode.Success){
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

    public Response<Boolean> addMission(Mission mission) {
        Response<Mission> missionResponse=missionRepo.save(mission);
        if(missionResponse.getReason()!= OpCode.Success){
            return new Response<>(false,missionResponse.getReason());
        }
        Response<List<Mission>> response =missionRepo.findAllMissions();
        return new Response<>(true,OpCode.Success);

    }

    /**
     * req 4.3 - search missions
     * @param apiKey - authentication object
     * @return - list of the missions were filtered
     */
    public Response<List<MissionData>> searchMissions(String apiKey){
        Response<Teacher> teacherResponse=checkTeacher(apiKey);
        if(teacherResponse.getReason()!=OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Response<List<Mission>> missionResponse=missionRepo.findAllMissions();
        if(missionResponse.getReason()!=OpCode.Success){
            return new Response<>(null,missionResponse.getReason());
        }
        return new Response<>(parseMissions(missionResponse.getValue()),OpCode.Success);
    }

    public List<MissionData> parseMissions(List<Mission> missions){
        List<MissionData> missionsData=new ArrayList<>();
        for(Mission mission: missions){
            MissionData md=new MissionData(mission.getMissionId(),mission.getMissionTypes());
            List<String> questList=new ArrayList<>();
            List<String> answerList = new ArrayList<>();
            if (mission instanceof KnownAnswerMission) {
                md.setName("Known answer mission");
                questList.add(((KnownAnswerMission) mission).getQuestion());
                md.setQuestion(questList);
                answerList.add(((KnownAnswerMission) mission).getRealAnswer());
                md.setAnswers(answerList);
            }
            if(mission instanceof OpenAnswerMission){
                md.setName("Open Answer Mission");
                questList.add(((OpenAnswerMission)mission).getQuestion());
            }
            if(mission instanceof StoryMission){
                md.setName("Story Mission");
                md.setTimeForAns(((StoryMission) mission).getSecondsForEachStudent());
            }
            if(mission instanceof TriviaMission){
                md.setName("Trivia Mission");
                md.setTimeForAns(((TriviaMission) mission).getSecondsForAnswer());
                for (Map.Entry<String, TriviaQuestion> entry : ((TriviaMission) mission).getQuestions().entrySet()){
                    questList.add(entry.getValue().getQuestion());
                }
                md.setQuestion(questList);
            }
            if(mission instanceof TrueLieMission){
                md.setName("True False Mission");
                md.setTimeForAns(((TrueLieMission) mission).getAnswerTimeForStudent());
            }
            missionsData.add(md);


        }
        return missionsData;
    }

}
