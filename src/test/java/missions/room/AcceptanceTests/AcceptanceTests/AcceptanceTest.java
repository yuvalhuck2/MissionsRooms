//package missions.room.AcceptanceTests.AcceptanceTests;
//
//import missions.room.AcceptanceTests.AcceptanceTestDataObjects.RegisterDetailsTest;
//import missions.room.AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
//import missions.room.AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static org.junit.jupiter.api.Assertions.fail;
//
//
//@Service
//public class AcceptanceTest {
//
//
//    protected static List<UserTestData> students = new ArrayList<>();
//    protected static List<UserTestData> teacher = new ArrayList<>();
//    protected final String testVerificationCode = "0";
//
//    @Autowired
//    protected AcceptanceTestsProxyBridge bridge;
//
//    public static class TestDataInitializer {
//        private static String studentPath = "src\\test\\java\\missions\\room\\AcceptanceTests\\AcceptanceTests\\student_test.csv";
//        private static String teacherPath = "src\\test\\java\\missions\\room\\AcceptanceTests\\AcceptanceTests\\teacher_test.csv";
//        private static String COMMA_DELIMITER = ",";
//
//        public static void getTestDataFromCsvReport() {
//            if (students.size() == 0){
//                getTestDataFromCsvReport(studentPath, students);
//                getTestDataFromCsvReport(teacherPath, teacher);
//            }
//        }
//
//        private static void getTestDataFromCsvReport(String file, List list) {
//            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//                String line;
//                boolean firstRow = true;
//                while ((line = br.readLine()) != null) {
//                    if(!firstRow){
//                        String[] studentParams = line.split(COMMA_DELIMITER);
//                        String alias = getAlias(studentParams[2]);
//                        list.add(new UserTestData(studentParams[0], studentParams[1], alias));
//                    }
//                    firstRow = false;
//                }
//            } catch (Exception e){
//
//            }
//        }
//
//        private static String getAlias(String email){
//            String pattern = "([^\\/]+)@leobaeck.net";
//
//            // Create a Pattern object
//            Pattern r = Pattern.compile(pattern);
//
//            // Now create matcher object.
//            Matcher m = r.matcher(email);
//            if (m.find( )) {
//                return m.group(1);
//            }else {
//                return null;
//            }
//        }
//
//    }
//
//    protected boolean setUpCSV(){
//        //login();
//        return true;
//    }
//
//    protected boolean registerCode(String alias, String code){
//        return bridge.registerCode(alias, code);
//    }
//
//    protected boolean register(RegisterDetailsTest registerDetailsTest){
//        return bridge.register(registerDetailsTest);
//    }
//
//    protected String login(){
//        String token = bridge.login("admin", "admin");
//        return token;
//    }
//
//    protected boolean deleteUser(){
//        return true;
//    }
//
////    @Test
////    public void testtest(){
////        fail();
////    }
//
//
//    public AcceptanceTest() {
//        TestDataInitializer.getTestDataFromCsvReport();
//    }
//}
