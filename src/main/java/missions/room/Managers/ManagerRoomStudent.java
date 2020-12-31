package missions.room.Managers;

import CrudRepositories.*;
import DataAPI.MissionData;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
import javafx.util.Pair;
import javassist.bytecode.Opcode;
import missions.room.Domain.*;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.missions.*;
import missions.room.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManagerRoomStudent extends StudentManager {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private ClassroomRoomRepo classroomRoomRepo;

    @Autowired
    private GroupRoomRepo groupRoomRepo;

    @Autowired
    private StudentRoomRepo studentRoomRepo;


    public ManagerRoomStudent() {
        super();
    }

    public ManagerRoomStudent(Ram ram, StudentCrudRepository studentCrudRepository, RoomCrudRepository roomCrudRepository, ClassroomRepository classroomRepository) {
        super(ram, studentCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
        this.classroomRepo = new ClassroomRepo(classroomRepository);
        //this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);
    }

    /**
     * req 3.6.1 - watch details of the room
     *
     * @param apiKey - authentication object
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<List<RoomDetailsData>> watchRoomDetails(String apiKey) {
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() != OpCode.Success) {
            return new Response(null, checkStudent.getReason());
        }
        Student student = checkStudent.getValue();
        Response<Classroom> classroomResponse = classroomRepo.findClassroomByStudent(apiKey);
        if (classroomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, classroomResponse.getReason());
        }
        Classroom classroom = classroomResponse.getValue();
        ClassGroup classGroup = classroom.getGroup(student.getAlias());
        if (classGroup == null) {
            return new Response<>(null, OpCode.Student_Not_Exist_In_Group);
        }
        Response<List<ClassroomRoom>> responseClass = classroomRoomRepo.findClassroomRoomByClassroom(classroom.getClassName());
        Response<List<GroupRoom>> responseGroup = groupRoomRepo.findGroupRoomByGroup(classGroup.getGroupName());
        Response<List<StudentRoom>> responseStudent = studentRoomRepo.findStudentRoomByStudent(student.getAlias());
        if (responseClass.getReason() == OpCode.DB_Error || responseGroup.getReason() == OpCode.DB_Error || responseStudent.getReason() == OpCode.DB_Error) {
            return new Response<>(null, OpCode.DB_Error);
        }
        List<Room> rooms = new ArrayList<>();

        if (responseClass.getReason() == OpCode.Success) {
            Response<List<Room>> responseRoomClass = getRoomsFromRoomClass(responseClass.getValue());
            if (responseRoomClass.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomClass.getReason());
            }
            rooms.addAll(responseRoomClass.getValue());
        }

        if (responseGroup.getReason() == OpCode.Success) {
            Response<List<Room>> responseRoomGroup = getRoomsFromRoomGroup(responseGroup.getValue());
            if (responseRoomGroup.getReason() != OpCode.Success) {
                return new Response<>(null, responseGroup.getReason());
            }
            rooms.addAll(responseRoomGroup.getValue());
        }

        if (responseStudent.getReason() == OpCode.Success) {
            Response<List<Room>> responseRoomStudent = getRoomsFromRoomStudent(responseStudent.getValue());
            if (responseRoomStudent.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomStudent.getReason());
            }
            rooms.addAll(responseRoomStudent.getValue());
        }

        if (rooms.size()==0){
            return new Response<>(null,OpCode.Empty);
        }

        return getRoomDetailFromRoom(rooms);

    }


    private Response<List<Room>> getRoomsFromRoomClass(List<ClassroomRoom> rooms) {
        List<Room> result = new ArrayList<>();
        for (ClassroomRoom classroomRoom : rooms) {
            String roomId = classroomRoom.getRoomId();
            Response<Room> response = GetRoomById(roomId);
            if (response.getReason() != OpCode.Success) {
                return new Response<>(null, response.getReason());
            }
            result.add(response.getValue());
        }
        return new Response<>(result, OpCode.Success);
    }

    private Response<List<Room>> getRoomsFromRoomGroup(List<GroupRoom> rooms) {
        List<Room> result = new ArrayList<>();
        for (GroupRoom groupRoom : rooms) {
            String roomId = groupRoom.getRoomId();
            Response<Room> response = GetRoomById(roomId);
            if (response.getReason() != OpCode.Success) {
                return new Response<>(null, response.getReason());
            }
            result.add(response.getValue());
        }
        return new Response<>(result, OpCode.Success);
    }

    private Response<List<Room>> getRoomsFromRoomStudent(List<StudentRoom> rooms) {
        List<Room> result = new ArrayList<>();
        for (StudentRoom studentRoom : rooms) {
            String roomId = studentRoom.getRoomId();
            Response<Room> response = GetRoomById(roomId);
            if (response.getReason() != OpCode.Success) {
                return new Response<>(null, response.getReason());
            }
            result.add(response.getValue());
        }
        return new Response<>(result, OpCode.Success);
    }

    private Response<Room> GetRoomById(String roomId) {
        if (ram.isRoomExist(roomId)) {
            return new Response(ram.getRoom(roomId), OpCode.Success);
        } else {
            Response<Room> response = roomRepo.findRoomById(roomId);
            if (response.getReason() == OpCode.Success) {
                ram.addRoom(roomId, response.getValue());
            }
            return response;

        }
    }


    private Response<List<RoomDetailsData>> getRoomDetailFromRoom(List<Room> rooms) {
        List<RoomDetailsData> roomDetailsDataList = new ArrayList<>();
        for (Room room : rooms) {
            Response<Mission> response = getMission(room.getCurrentMission(), room.getRoomTemplate());
            if (response.getReason() != OpCode.Success) {
                return new Response(null, response.getReason());
            }
            MissionData missionData = getMissionData(response.getValue());
            RoomDetailsData roomDetailsData=new RoomDetailsData(room.getRoomId(),room.getName(),missionData);
            roomDetailsDataList.add(roomDetailsData);
        }
        return new Response<>(roomDetailsDataList,OpCode.Success);
    }

    private Response<Mission> getMission(int missionIndex, RoomTemplate roomTemplate) {
        Mission m = roomTemplate.getMission(missionIndex);
        if (m == null) {
            return new Response<>(null, OpCode.Wrong_Mission_Index);
        }
        return new Response<>(m, OpCode.Success);

    }

    private MissionData getMissionData(Mission mission) {
        MissionData md = new MissionData(mission.getMissionId(), mission.getMissionTypes());
        List<String> questList = new ArrayList<>();
        if (mission instanceof KnownAnswerMission) {
            md.setName("Known answer mission");
            questList.add(((KnownAnswerMission) mission).getQuestion());
            md.setQuestion(questList);
        }
        if (mission instanceof OpenAnswerMission) {
            md.setName("Open Answer Mission");
            questList.add(((OpenAnswerMission) mission).getQuestion());
        }
        if (mission instanceof StoryMission) {
            md.setName("Story Mission");
            md.setTimeForAns(((StoryMission) mission).getSecondsForEachStudent());
        }
        if (mission instanceof TriviaMission) {
            md.setName("Trivia Mission");
            md.setTimeForAns(((TriviaMission) mission).getSecondsForAnswer());
            for (Map.Entry<String, TriviaQuestion> entry : ((TriviaMission) mission).getQuestions().entrySet()) {
                questList.add(entry.getValue().getQuestion());
            }
            md.setQuestion(questList);
        }
        if (mission instanceof TrueLieMission) {
            md.setName("True False Mission");
            md.setTimeForAns(((TrueLieMission) mission).getAnswerTimeForStudent());
        }
        return md;
    }





}
