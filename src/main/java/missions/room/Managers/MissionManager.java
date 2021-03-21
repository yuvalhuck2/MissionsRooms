package missions.room.Managers;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import ExternalSystems.UniqueStringGenerator;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.RoomOpenAnswersView;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.missions.OpenAnswerMission;
import missions.room.Repo.MissionRepo;
import missions.room.Repo.OpenAnswerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionManager extends TeacherManager {

    private final Gson gson;
    private static final String missionName="mis";

    @Autowired
    private MissionRepo missionRepo;

    @Autowired
    private OpenAnswerRepo openAnswerRepo;

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

    private List<RoomOpenAnswerData> convertRoomOpenAnswerViewListToRoomOpenAnswerDataList(List<RoomOpenAnswersView> roomOpenAnswersViews) {
        List<RoomOpenAnswerData> roomOpenAnswerDataList = new ArrayList<>();
        for(RoomOpenAnswersView roomOpenAnswersView : roomOpenAnswersViews) {
            roomOpenAnswerDataList.add(convertRoomOpenAnswerViewToSolutionData(roomOpenAnswersView));
        }
        return roomOpenAnswerDataList;
    }

    private RoomOpenAnswerData convertRoomOpenAnswerViewToSolutionData(RoomOpenAnswersView roomOpenAnswersView) {
        List<OpenAnswer> openAnswers = roomOpenAnswersView.getOpenAnswers();
        RoomTemplate roomTemplate = roomOpenAnswersView.getRoomTemplate();
        List<SolutionData> solutionDataList = convertOpenAnswerListToSolutionDataList(openAnswers, roomTemplate);
        return new RoomOpenAnswerData(roomOpenAnswersView.getRoomId(), roomOpenAnswersView.getName(), solutionDataList);
    }

    private List<SolutionData> convertOpenAnswerListToSolutionDataList(List<OpenAnswer> openAnswers, RoomTemplate roomTemplate) {
        List<SolutionData> solutionDataList = new ArrayList<>();
        for(OpenAnswer openAnswer : openAnswers) {
            OpenAnswerMission relevantMission = (OpenAnswerMission)(roomTemplate.getMission(openAnswer.getMissionId()));
            solutionDataList.add(convertOpenAnswerToSolutionData(openAnswer, relevantMission.getQuestion()));
        }
        return solutionDataList;
    }

    private SolutionData convertOpenAnswerToSolutionData(OpenAnswer openAnswer, String missionQuestion) {
        return new SolutionData(openAnswer.getMissionId(), openAnswer.getOpenAnswerText(), openAnswer.isHasFile(), missionQuestion);
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
        List<MissionData> missionDataList=missionResponse.getValue()
                .parallelStream().map(Mission::getData)
                .collect(Collectors.toList());
        return new Response<>(missionDataList,OpCode.Success);
    }

    /**
     * req 4.9 - watch students solutions
     * @return all the solutions that wait to be approved
     */
    public Response<RoomOpenAnswerData> watchSolutions(String apiKey, String roomId){
        Response<Teacher> teacherResponse = checkTeacher(apiKey); //TODO uncomment
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Response<RoomOpenAnswersView> openAnswerResponse = openAnswerRepo.getOpenAnswers(teacherResponse.getValue().getAlias(), roomId);
        if(openAnswerResponse.getReason() == OpCode.Success) {
            return new Response<>(convertRoomOpenAnswerViewToSolutionData(openAnswerResponse.getValue()), OpCode.Success);
        }
        return new Response<>(null, openAnswerResponse.getReason());
    }
}
