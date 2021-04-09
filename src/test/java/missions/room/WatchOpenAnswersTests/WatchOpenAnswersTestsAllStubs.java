package missions.room.WatchOpenAnswersTests;

import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomOpenAnswerData;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.Ram;
import missions.room.Domain.RoomOpenAnswersView;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.MissionManager;
import missions.room.Repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static Data.DataConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WatchOpenAnswersTestsAllStubs {


    protected DataGenerator dataGenerator;

    protected String apiKey;

    protected Ram ram;


    /**
     * working with mockitos
     */

    @InjectMocks
    protected MissionManager missionManagerWithMockito;

    @Mock
    protected Ram mockRam;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected OpenAnswerRepo mockOpenAnswerRepo;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        apiKey="key";
        initMocks();
    }

    protected void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        Teacher teacher = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        String teacherAlias = dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias();
        Room room = dataGenerator.getRoom(Data.VALID_OPEN_ANS);

        when(mockRam.getAlias(apiKey))
                .thenReturn(teacherAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_TEACHER_KEY))
                .thenReturn(WRONG_TEACHER_NAME);
        when(mockTeacherRepo.findTeacherById(teacherAlias))
                .thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockOpenAnswerRepo.getOpenAnswers(teacherAlias, room.getRoomId()))
                .thenReturn(new Response<>(new RoomOpenAnswer(), OpCode.Success));
        when(mockOpenAnswerRepo.getOpenAnswers(eq(teacherAlias), AdditionalMatchers.not(eq(room.getRoomId()))))
                .thenReturn(new Response<>(null, OpCode.INVALID_ROOM_ID));



    }

    void setUpMocks(){
//        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
//        classroomRepo=new ClassRoomRepositoryMock(dataGenerator);
//        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
//        groupRepository=new ClassGroupRepositoryMock(dataGenerator);
//        ram=new MockRam(dataGenerator);
//        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);
//
//        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
//        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
//        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
//        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
    }

    private class RoomOpenAnswer implements RoomOpenAnswersView {

        @Override
        public String getRoomId() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public List<OpenAnswer> getOpenAnswers() {
            return new ArrayList<>();
        }

        @Override
        public RoomTemplate getRoomTemplate() {
            return null;
        }
    }

    @Test
    void testWatchSolutionsSuccess(){
        Response<RoomOpenAnswerData> res =  missionManagerWithMockito.watchSolutions(apiKey, dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId());
        assertEquals(res.getReason(), OpCode.Success);
    }

    @Test
    void testWatchSolutionFailInvalidApiKey() {
        Response<RoomOpenAnswerData> res =  missionManagerWithMockito.watchSolutions(INVALID_KEY, dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId());
        assertEquals(res.getReason(), OpCode.Wrong_Key);
    }

    @Test
    void testWatchSolutionFailInvalidRoom() {
        Response<RoomOpenAnswerData> res =  missionManagerWithMockito.watchSolutions(apiKey,"");
        assertEquals(res.getReason(), OpCode.INVALID_ROOM_ID);
    }


}
