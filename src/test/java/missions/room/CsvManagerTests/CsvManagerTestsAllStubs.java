package missions.room.CsvManagerTests;

import Data.Data;
import Data.DataGenerator;
import DataObjects.APIObjects.StudentData;
import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.IT;
import missions.room.Managers.UploadCsvManager;
import missions.room.Repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;

import static Data.DataConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CsvManagerTestsAllStubs {
    private final String csvType = "csv/text";
    private final String textType = "text/plain";
    private String classesFileContent = "Email,MembersCount,Members\r\nlb72-1@leobaeck.net,40,\"roy4@leobaeck.net\nronilach@leobaeck.net\"\r\n";
    private String studentFileContent = "First Name [Required],Last Name [Required],Email Address [Required]\r\nרוי,לוי,roy4@leobaeck.net\r\nניב,שיראזי,ronilach@leobaeck.net\r\n";
    private String groupsFileContent = "class,A,B\r\nי1,טל כהן,יובל סבג\r\n";
    private String teacherFileContent = "First Name [Required],Last Name [Required],Email Address [Required]\r\nטל,כהן,tal9@leobaeck.net\r\nיובל,סבג,sabay@leobaeck.net\r\n";
    private String TEACHER_FILE = "teachers.csv";
    private String STUDENT_FILE = "students.csv";
    private String CLASSES_FILE = "classes.csv";
    private String GROUPS_FILE = "groups.csv";
    private Map<String, String> files = new HashMap<String, String>() {{
        put(TEACHER_FILE, teacherFileContent);
        put(STUDENT_FILE, studentFileContent);
        put(CLASSES_FILE, classesFileContent);
        put(GROUPS_FILE, groupsFileContent);
    }};
    protected MultipartFile[] multipartArray;

    @InjectMocks
    protected UploadCsvManager uploadCsvManager;

    @Mock
    protected Ram mockRam;

    @Mock
    protected ITRepo mockITRepo;

    @Mock
    protected ClassroomRepo mockClassroomRepo;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    protected DataGenerator dataGenerator;

    protected String ITApiKey;

    protected UserProfileData userProfileData;

    protected TeacherData teacherData;

    protected StudentData studentData;

    private AutoCloseable closeable;

    protected String classroomName;


    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        ITApiKey = IT_API_KEY;
        userProfileData = dataGenerator.getProfileData(Data.VALID);
        teacherData = dataGenerator.getTeacherData(Data.VALID);
        studentData = dataGenerator.getStudentData(Data.VALID_STUDENT);
        initMocks();
        initFile();
    }

    private void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        IT it = (IT) dataGenerator.getUser(Data.VALID_IT);
        String itAlias = it.getAlias();

        IT it2 = (IT) dataGenerator.getUser(Data.VALID_IT2);
        String itAlias2 = it2.getAlias();

        Classroom empty = dataGenerator.getClassroom(Data.Empty_Students);
        classroomName = empty.getClassName();

        initRam(itAlias);
        initClassroomRepo();
        initTeacherRepo();
        initITRepo(it, itAlias, it2);
    }

    protected void initClassroomRepo() {
        when(mockClassroomRepo.saveAll(any()))
                .thenReturn(new Response<>(true,OpCode.Success));
    }

    protected void initTeacherRepo() {
        when(mockTeacherRepo.saveAll(any()))
                .thenReturn(new Response<>(true,OpCode.Success));
    }



    protected void initITRepo(IT it, String itAlias, IT it2) {
        when(mockITRepo.findITById(itAlias))
                .thenReturn(new Response<>(it,OpCode.Success));
        when(mockITRepo.findITById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
    }



    protected void initRam(String itAlias) {
        when(mockRam.getAlias(IT_API_KEY))
                .thenReturn(itAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
    }

    protected void initFile(){
        this.multipartArray = new MultipartFile[4];
        int i = 0;
        for (Map.Entry<String, String> file : files.entrySet()){
            byte[] content = file.getValue().getBytes();
            multipartArray[i] = createFile(file.getKey(), csvType, content);
            i++;
        }
    }

    private MultipartFile createFile(String fileName, String fileType, byte[] fileContent) {
        return new MockMultipartFile(fileName, fileName, fileType, fileContent);
    }

    private void replaceFile(String fileName, MultipartFile mockMultipartFile) {
        for (int i = 0 ; i < multipartArray.length; i++){
            if(multipartArray[i].getName().equals(fileName)){
                multipartArray[i] = mockMultipartFile;
                break;
            }
        }
    }

    @Test
    void uploadCSVSuccess(){
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(true, response.getValue());
        assertEquals(OpCode.Success, response.getReason());
    }

    @Test
    void setUploadCsvFailInvalidKey(){
        Response<Boolean> response = uploadCsvManager.uploadCsv(INVALID_KEY, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.Wrong_Key, response.getReason());
    }

    @Test
    void uploadCSVFailWrongCSVFileNumber(){
        MultipartFile[] partialMultipartArray = new MultipartFile[3];
        for (int i = 0; i < partialMultipartArray.length; i++) {
            partialMultipartArray[i] = multipartArray[i];
        }
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, partialMultipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.Wrong_File_Name, response.getReason());
    }

    @Test
    void uploadCSVFailWrongFileType(){
        byte[] fileContent = "mock file".getBytes();
        MultipartFile mockFile = createFile(GROUPS_FILE, textType, fileContent);
        MultipartFile[] partialMultipartArray = new MultipartFile[4];
        partialMultipartArray[0] = mockFile;
        int i = 1;
        for (Map.Entry<String, String> file : files.entrySet()){
            String name = file.getKey();
            if (!name.equals(GROUPS_FILE)){
                byte[] content = file.getValue().getBytes();
                partialMultipartArray[i] = createFile(file.getKey(), csvType, content);
                i++;
            }
        }

        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, partialMultipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.WrongFileExt, response.getReason());
    }

    @Test
    void uploadCSVFailWrongFileName(){
        multipartArray[0] = createFile("invalidName.csv", csvType, files.get(multipartArray[0].getName()).getBytes());
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.Wrong_File_Name, response.getReason());
    }

    @Test
    void uploadCSVFailWrongFileHeaders(){
        String invalidTeacherContent = teacherFileContent.replace("Email", "Emmail");
        MultipartFile mockMultipartFile = createFile(TEACHER_FILE, csvType, invalidTeacherContent.getBytes());
        replaceFile(TEACHER_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.Wrong_File_Headers, response.getReason());
    }

    @Test
    void uploadCSVFailInvalidTeacherMail() {
        String invalidTeacherContent = teacherFileContent.replace("leobaeck", "leoback");
        MultipartFile mockMultipartFile = createFile(TEACHER_FILE, csvType, invalidTeacherContent.getBytes());
        replaceFile(TEACHER_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.InvalidTeacherMail, response.getReason());
    }
    @Test
    void uploadCSVFailInvalidStudentMail() {
        String invalidStudentsContent = studentFileContent.replace("leobaeck", "leoback");
        MultipartFile mockMultipartFile = createFile(STUDENT_FILE, csvType, invalidStudentsContent.getBytes());
        replaceFile(STUDENT_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.InvalidStudentMail, response.getReason());
    }
    @Test
    void uploadCSVFailInvalidClassMail() {
        String invalidClassContent = classesFileContent.replace("leobaeck", "leoback");
        MultipartFile mockMultipartFile = createFile(CLASSES_FILE, csvType, invalidClassContent.getBytes());
        replaceFile(CLASSES_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.InvalidClassMail, response.getReason());
    }
    @Test
    void uploadCSVFailInvalidClassMailPrefix() {
        String invalidClassContent = classesFileContent.replace("lb", "rb");
        MultipartFile mockMultipartFile = createFile(CLASSES_FILE, csvType, invalidClassContent.getBytes());
        replaceFile(CLASSES_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.Wrong_File_Headers, response.getReason());
    }
    @Test
    void uploadCSVFailInvalidClassName() {
        String invalidGroupsContent = groupsFileContent.replace("י1", "י");
        MultipartFile mockMultipartFile = createFile(GROUPS_FILE, csvType, invalidGroupsContent.getBytes());
        replaceFile(GROUPS_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.InvalidClassName, response.getReason());

    }
    @Test
    void uploadCSVFailClassNotFound() {
        String invalidGroupsContent = groupsFileContent.replace("י1", "יא1");
        MultipartFile mockMultipartFile = createFile(GROUPS_FILE, csvType, invalidGroupsContent.getBytes());
        replaceFile(GROUPS_FILE, mockMultipartFile);
        Response<Boolean> response = uploadCsvManager.uploadCsv(ITApiKey, multipartArray);
        assertEquals(false, response.getValue());
        assertEquals(OpCode.ClassNotFound, response.getReason());

    }
}
