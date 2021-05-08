package missions.room.AcceptanceTests.AcceptanceTests;

import com.sun.deploy.cache.BaseLocalApplicationProperties;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;
import missions.room.AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.javascript.navig.Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.fail;


@Service
public class AcceptanceTest {


    protected static List<UserTestData> students = new ArrayList<>();
    protected static List<UserTestData> teacher = new ArrayList<>();
    protected static List<RoomTemplateDetailsTestData> templatesData = new ArrayList<>();
    protected static List<RoomDetailsTest> roomDetails = new ArrayList<>();
    protected static List<MissionDetailsTest> missionData = new ArrayList<>();
    protected final String testVerificationCode = "0";

    private String itToken;

    @Autowired
    protected AcceptanceTestsProxyBridge bridge;

    public static class TestDataInitializer {
        private static String studentPath = "src\\test\\java\\missions\\room\\AcceptanceTests\\AcceptanceTests\\student_test.csv";
        private static String teacherPath = "src\\test\\java\\missions\\room\\AcceptanceTests\\AcceptanceTests\\teacher_test.csv";
        private static String COMMA_DELIMITER = ",";

        public static void getTestDataFromCsvReport() {
            if (students.size() == 0){
                getTestDataFromCsvReport(studentPath, students);
                getTestDataFromCsvReport(teacherPath, teacher);
            }
        }

        private static void getTestDataFromCsvReport(String file, List list) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstRow = true;
                while ((line = br.readLine()) != null) {
                    if(!firstRow){
                        String[] studentParams = line.split(COMMA_DELIMITER);
                        String alias = getAlias(studentParams[2]);
                        list.add(new UserTestData(studentParams[0], studentParams[1], alias));
                    }
                    firstRow = false;
                }
            } catch (Exception e){

            }
        }

        private static String getAlias(String email){
            String pattern = "([^\\/]+)@leobaeck.net";

            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            // Now create matcher object.
            Matcher m = r.matcher(email);
            if (m.find( )) {
                return m.group(1);
            }else {
                return null;
            }
        }

        public static void initAddRoomData() {
            List<String> missions = Arrays.asList("story1", "open1", "mid1");
            RoomTemplateDetailsTestData roomTemplateDetailsTestData =
                    new RoomTemplateDetailsTestData(missions, "name", 0, ParticipantTypeTest.Class);
            templatesData.add(roomTemplateDetailsTestData);

            roomDetails.add(new RoomDetailsTest("roomName", 3, "2=4", ParticipantTypeTest.Class));

            missionData.add(new MissionDetailsTest("story1",
                    Arrays.asList(ParticipantTypeTest.Personal, ParticipantTypeTest.Class, ParticipantTypeTest.Group),
                    ""));
        }
    }

    protected boolean setUpCSV(){
        itToken = login();
        return true;
    }

    protected boolean registerCode(String alias, String code, String teacherRegisterDetailsAlias){
        return bridge.registerCode(alias, code, teacherRegisterDetailsAlias);
    }

    protected boolean register(RegisterDetailsTest registerDetailsTest){
        return bridge.register(registerDetailsTest);
    }

    protected boolean registerCodeTeacher(String alias, String verificationCode) {
        return bridge.registerCodeTeacher(alias, verificationCode);
    }

    protected boolean teacherLogin(String alias, String password){
        return bridge.teacherLogin(alias, password);
    }

    protected String login(){
        String token = bridge.login("admin", "admin");
        return token;
    }

    protected boolean createTemplate(RoomTemplateDetailsTestData roomTemplateDetailsTestData){
        return bridge.addRoomTemplate(roomTemplateDetailsTestData);
    }

    protected boolean createRoom(RoomDetailsTest roomDetailsTest){
        return bridge.openRoom(roomDetailsTest);
    }

    protected RoomDetailsTest watchRoomDetails(){
        return bridge.watchRoomDetails();
    }

    public AcceptanceTest() {
        TestDataInitializer.getTestDataFromCsvReport();
        TestDataInitializer.initAddRoomData();
    }
}
