package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.RegisterDetailsTest;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestBridge;
import AcceptanceTests.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
public class AcceptanceTest {

    @Autowired
    protected AcceptanceTestsProxyBridge bridge;

    protected static List<UserTestData> students = new ArrayList<>();
    protected static List<UserTestData> teacher = new ArrayList<>();


    public static class TestDataInitializer {
        private static String studentPath = "src\\test\\java\\AcceptanceTests\\AcceptanceTests\\student_test.csv";
        private static String teacherPath = "src\\test\\java\\AcceptanceTests\\AcceptanceTests\\teacher_test.csv";
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

    }
    public AcceptanceTest(){
        //this.bridge = AcceptanceTestDriver.getBridge();

        TestDataInitializer.getTestDataFromCsvReport();
    }

    protected boolean setUpCSV(){
        return true;
    }

    protected boolean register(RegisterDetailsTest registerDetailsTest){
        return bridge.register(registerDetailsTest);
    }

    protected boolean deleteUser(){
        return true;
    }

}
