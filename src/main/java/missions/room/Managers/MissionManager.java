package missions.room.Managers;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataObjects.APIObjects.RoomOpenAnswerData;
import DataObjects.APIObjects.SolutionData;
import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import ExternalSystems.UniqueStringGenerator;
import Utils.InterfaceAdapter;
import Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.apachecommons.CommonsLog;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.Ram;
import missions.room.Domain.RoomOpenAnswersView;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Teacher;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.missions.OpenAnswerMission;
import missions.room.Repo.MissionRepo;
import missions.room.Repo.OpenAnswerRepo;
import missions.room.Repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CommonsLog
public class MissionManager extends TeacherManager {

    private final Gson gson;
    private static final String missionName="mis";

    @Autowired
    private MissionRepo missionRepo;

    @Autowired
    private OpenAnswerRepo openAnswerRepo;

    @Autowired
    private RoomRepo roomRepo;

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
        if(teacherResponse.getReason()!= OpCode.Success){
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
        catch (Exception e){
//            log.error("wrong format mission "+ missionData.toString(), e);
        }
        return new Response<>(null,OpCode.Not_Mission);
    }

    private RoomOpenAnswerData convertRoomOpenAnswerViewToSolutionData(RoomOpenAnswersView roomOpenAnswersView) {
        List<OpenAnswer> openAnswers = roomOpenAnswersView.getOpenAnswers();
        RoomTemplate roomTemplate = roomOpenAnswersView.getRoomTemplate();
        String roomId = roomOpenAnswersView.getRoomId();
        List<SolutionData> solutionDataList = convertOpenAnswerListToSolutionDataList(openAnswers, roomTemplate, roomId);
        return new RoomOpenAnswerData(roomOpenAnswersView.getRoomId(), roomOpenAnswersView.getName(), solutionDataList);
    }

    private List<SolutionData> convertOpenAnswerListToSolutionDataList(List<OpenAnswer> openAnswers, RoomTemplate roomTemplate, String roomId) {
        List<SolutionData> solutionDataList = new ArrayList<>();
        for(OpenAnswer openAnswer : openAnswers) {
            OpenAnswerMission relevantMission = (OpenAnswerMission)(roomTemplate.getMission(openAnswer.getMissionId()));
            solutionDataList.add(convertOpenAnswerToSolutionData(openAnswer, relevantMission.getQuestion(), roomId));
        }
        return solutionDataList;
    }

    private SolutionData convertOpenAnswerToSolutionData(OpenAnswer openAnswer, String missionQuestion, String roomId) {
        SolutionData solutionData = new SolutionData(openAnswer.getMissionId(), openAnswer.getOpenAnswerText(), openAnswer.isHasFile(), missionQuestion);
        if (openAnswer.isHasFile()) {
            solutionData.setFileName(getFileName(openAnswer.getMissionId(), roomId));
        }
        return solutionData;
    }

    private String getFileName(String missionId, String roomId) {
        Response<File> fileRes = getOpenAnswerFile(roomId, missionId);
        File file = fileRes.getValue();
        if (file == null) {
            log.error("null file name");
            return "";
        }
        return file.getName();
    }

    public Response<Boolean> addMission(Mission mission) {
        Response<Mission> missionResponse=missionRepo.save(mission);
        if(missionResponse.getReason()!= OpCode.Success){
            return new Response<>(false,missionResponse.getReason());
        }
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
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Response<RoomOpenAnswersView> openAnswerResponse = openAnswerRepo.getOpenAnswers(teacherResponse.getValue().getAlias(), roomId);
        if(openAnswerResponse.getReason() == OpCode.Success) {
            return new Response<>(convertRoomOpenAnswerViewToSolutionData(openAnswerResponse.getValue()), OpCode.Success);
        }
        return new Response<>(null, openAnswerResponse.getReason());
    }


    private Response<Room> getRoomById(String roomId) {
        Room room = ram.getRoom(roomId);
        if (room==null) {
            Response<Room> response = roomRepo.findRoomById(roomId);
            if (response.getReason() == OpCode.Success) {
                if (response.getValue() == null) {
                    return new Response<>(null, OpCode.Not_Exist_Room);
                }
                ram.addRoom(response.getValue());
                room = ram.getRoom(roomId);
            } else {
                return response;
            }
        }
        return new Response<>(room, OpCode.Success);
    }

    public Response<File> getMissionOpenAnswerFile(String apiKey, String roomId, String missionId) {
        Response<Teacher> teacherResponse = checkTeacher(apiKey);
        if(teacherResponse.getReason() != OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }

        Response<Room> roomResponse = getRoomById(roomId);
        if (roomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, roomResponse.getReason());
        }

        if (!roomResponse.getValue().isMissionExists(missionId)) {
            return new Response<>(null, OpCode.MISSION_NOT_IN_ROOM);
        }

        return getOpenAnswerFile(roomId, missionId);
    }

    private Response<File> getOpenAnswerFile(String roomId, String missionId) {
        try {
            String rootPath = Utils.getRootDirectory();
            Path folderPath = FileSystems.getDefault().getPath(rootPath,"openAnswer", roomId, missionId);
            File folder = folderPath.toFile();
            if (folder.listFiles().length > 0) {
                File file = folderPath.toFile().listFiles()[0];
                return new Response<>(file, OpCode.Success);
            }
            else {
                return  new Response<>(null, OpCode.NO_OPEN_ANSWER_FILE);
            }
        } catch (Exception e) {
            log.error("cannot read file path", e);
            return new Response<>(null, OpCode.FILE_SYS_ERROR);
        }

    }
}
