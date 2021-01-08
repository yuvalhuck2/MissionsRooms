package missions.room.Managers;

import CrudRepositories.*;
import DataAPI.*;
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
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private ClassGroupRepo classGroupRepo;





    public ManagerRoomStudent() {
        super();
    }

    public ManagerRoomStudent(Ram ram, StudentCrudRepository studentCrudRepository, RoomCrudRepository roomCrudRepository, ClassroomRepository classroomRepository
                              ,GroupRepository groupRepository) {
        super(ram, studentCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
        this.classroomRepo = new ClassroomRepo(classroomRepository);
        this.classGroupRepo=new ClassGroupRepo(groupRepository);
        //this.roomTemplateRepo=new RoomTemplateRepo(roomTemplateCrudRepository);
    }

    /**
     * req 3.6.2.3 - answer deterministic question mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @param answer - answer for the question
     * @return if the answer was correct
     */
    //TODO now we return always true if every thing alright ,otherwise return null and error opcode
    //TODO need to change the response to the next mission if exists
    @Transactional
    public Response<Boolean> answerDeterministicQuestion(String apiKey, String roomId, boolean answer){
        Response<Student> checkStudent = checkStudent(apiKey);
        if (checkStudent.getReason() != OpCode.Success) {
            return new Response(null, checkStudent.getReason());
        }
        Student student = checkStudent.getValue();
        Response<Room> roomResponse=roomRepo.findRoomForWrite(roomId);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(null,roomResponse.getReason());
        }
        Room room=roomResponse.getValue();
        boolean isLastMission=room.getCurrentMission()==room.getRoomTemplate().getMissions().size()-1;
        if(answer) {
            room.addCorrectAnswer();
            int points = room.getRoomTemplate().getMission(room.getCurrentMission()).getPoints();
            if (room instanceof ClassroomRoom) {
                Classroom classroom = ((ClassroomRoom) room).getParticipant();
                classroom.addPoints(points);
                if(isLastMission&&room.getRoomTemplate().getMinimalMissionsToPass()<=room.getCountCorrectAnswer()){
                    classroom.addPoints(room.getBonus());
                }
                Response<Classroom> response=classroomRepo.save(classroom);
                if(response.getReason()!=OpCode.Success){
                    return new Response<>(null,response.getReason());
                }
            }
            if (room instanceof GroupRoom) {
                ClassGroup classGroup = ((GroupRoom) room).getParticipant();
                classGroup.addPoints(points);
                if(isLastMission&&room.getRoomTemplate().getMinimalMissionsToPass()<=room.getCountCorrectAnswer()){
                    classGroup.addPoints(room.getBonus());
                }

                Response<ClassGroup> response=classGroupRepo.save(classGroup);
                if(response.getReason()!=OpCode.Success){
                    return new Response<>(null,response.getReason());
                }
            }
            if (room instanceof StudentRoom) {
                student.addPoints(points);
                if(isLastMission&&room.getRoomTemplate().getMinimalMissionsToPass()<=room.getCountCorrectAnswer()){
                    student.addPoints(room.getBonus());
                }

                Response<SchoolUser> response=studentRepo.save(student);
                if(response.getReason()!=OpCode.Success){
                    return new Response<>(null,response.getReason());
                }
            }
        }
        if(room.getCurrentMission()!=room.getRoomTemplate().getMissions().size()-1){
            room.increaseCurrentMission();
            Response<Room> response=roomRepo.save(room);
            if(response.getReason()!=OpCode.Success){
                return new Response<>(null,response.getReason());
            }
            return new Response<>(true,OpCode.Success);
        }
        else{
            Response<Boolean> response=roomRepo.deleteRoom(room);
            if(response.getReason()!=OpCode.Success){
                return new Response<>(null,response.getReason());
            }
            return new Response<>(true,OpCode.Final);
        }

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
        Response<Classroom> classroomResponse = classroomRepo.findClassroomByStudent(student.getAlias());
        if (classroomResponse.getReason() != OpCode.Success) {
            return new Response<>(null, classroomResponse.getReason());
        }
        Classroom classroom = classroomResponse.getValue();

        if (classroom == null){
            return new Response<>(null, OpCode.Student_Not_Exist_In_Class);
        }

        ClassGroup classGroup = classroom.getGroup(student.getAlias());
        if (classGroup == null) {
            return new Response<>(null, OpCode.Student_Not_Exist_In_Group);
        }

        Response<ClassroomRoom> responseClass = roomRepo.findClassroomRoomByAlias(classroom.getClassName());
        Response<GroupRoom> responseGroup =roomRepo.findGroupRoomByAlias(classGroup.getGroupName());
        Response<StudentRoom> responseStudent = roomRepo.findStudentRoomByAlias(student.getAlias());
        if (responseClass.getReason() == OpCode.DB_Error || responseGroup.getReason() == OpCode.DB_Error || responseStudent.getReason() == OpCode.DB_Error) {
            return new Response<>(null, OpCode.DB_Error);
        }
        List<Room> rooms = new ArrayList<>();

        if (responseClass.getReason() == OpCode.Success&&responseClass.getValue()!=null) {
            Response<Room> responseRoomClass = getRoomsFromRoomClass(responseClass.getValue());
            if (responseRoomClass.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomClass.getReason());
            }
            rooms.add(responseRoomClass.getValue());
        }

        if (responseGroup.getReason() == OpCode.Success&&responseGroup.getValue()!=null) {
            Response<Room> responseRoomGroup = getRoomsFromRoomGroup(responseGroup.getValue());
            if (responseRoomGroup.getReason() != OpCode.Success) {
                return new Response<>(null, responseGroup.getReason());
            }
            rooms.add(responseRoomGroup.getValue());
        }

        if (responseStudent.getReason() == OpCode.Success&&responseStudent.getValue()!=null) {
            Response<Room> responseRoomStudent = getRoomsFromRoomStudent(responseStudent.getValue());
            if (responseRoomStudent.getReason() != OpCode.Success) {
                return new Response<>(null, responseRoomStudent.getReason());
            }
            rooms.add(responseRoomStudent.getValue());
        }

        if (rooms.size()==0){
            return new Response<>(new ArrayList<>(),OpCode.Success);
        }
        return getRoomDetailFromRoom(rooms);
    }

    private Response<Room> getRoomsFromRoomClass(ClassroomRoom classroomRoom) {
        String roomId = classroomRoom.getRoomId();
        Response<Room> response = GetRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
    }

    private Response<Room> getRoomsFromRoomGroup(GroupRoom groupRoom) {
        String roomId = groupRoom.getRoomId();
        Response<Room> response = GetRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
    }

    private Response<Room> getRoomsFromRoomStudent(StudentRoom studentRoom) {
        String roomId = studentRoom.getRoomId();
        Response<Room> response = GetRoomById(roomId);
        if (response.getReason() != OpCode.Success) {
            return new Response<>(null, response.getReason());
        }
        return new Response<>(response.getValue(), OpCode.Success);
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
                return new Response<>(null, response.getReason());
            }
            MissionData missionData = getMissionData(response.getValue());

            RoomDetailsData roomDetailsData=new RoomDetailsData(room.getRoomId(),room.getName(),missionData,room.getRoomTemplate().getType());
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
        List<String> answerList = new ArrayList<>();
        if (mission instanceof KnownAnswerMission) {
            md.setName("Known answer mission");
            questList.add(((KnownAnswerMission) mission).getQuestion());
            md.setQuestion(questList);
            answerList.add(((KnownAnswerMission) mission).getRealAnswer());
            md.setAnswers(answerList);
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
