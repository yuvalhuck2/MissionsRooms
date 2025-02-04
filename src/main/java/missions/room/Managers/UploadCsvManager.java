package missions.room.Managers;

import DataObjects.FlatDataObjects.GroupType;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import javafx.util.Pair;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import missions.room.Domain.Ram;
import missions.room.Domain.Users.IT;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.ITRepo;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class UploadCsvManager extends ITManager{
    private static final String TEACHER_FILE = "teachers.csv";
    private static final String STUDENT_FILE = "students.csv";
    private static final String[] USERS_FILE_HEADERS = {"First Name", "Last Name", "Email Address"};
    private static final int FIRST_NAME = 0;
    private static final int LAST_NAME = 1;
    private static final int EMAIL = 2;

    private static final String CLASSES_FILE = "classes.csv";
    private static final String[] CLASSES_FILE_HEADERS = {"Email", "MembersCount", "Members"};
    private static final int CLASS_EMAIL = 0;
    private static final int MEMBERS = 2;

    private static final String GROUPS_FILE = "groups.csv";
    private static final String[] GROUPS_FILE_HEADERS = {"class", "A", "B"};
    private static final int CLASS_NAME = 0;
    private static final int GROUP_A_TEACHER = 1;
    private static final int GROUP_B_TEACHER = 2;
    private static final String YUD = "2";
    private static final String YUD_A = "1";
    private static final String YUD_B = "0";



    private static final String NEW_LINE_DEL = "\\r?\\n";
    private static final String COMMA_DEL =",";

    @Autowired
    private ClassroomRepo classRoomRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ITRepo itRepo;

    private Ram ram;

    public UploadCsvManager() {
        ram = new Ram();
    }

    public Response<Boolean> uploadCsv(String token, MultipartFile[] CSVs) {
        Response<IT> checkToken = checkIT(token);
        if (checkToken.getValue() == null) {
            return new Response<>(false, checkToken.getReason());
        }
        if (!checkFilesExtension(CSVs)) {
            return new Response<>(false, OpCode.WrongFileExt);
        }
        String studentsFile, classesFile, teacherFile, groupsFile;
        try{
            studentsFile = getFileStringByName(CSVs, STUDENT_FILE);
            classesFile = getFileStringByName(CSVs, CLASSES_FILE);
            teacherFile = getFileStringByName(CSVs, TEACHER_FILE);
            groupsFile = getFileStringByName(CSVs, GROUPS_FILE);
        } catch(NoSuchElementException ex){
            return new Response<>(false, OpCode.Wrong_File_Name, "expected file names: " +TEACHER_FILE+ " "+STUDENT_FILE+" "+CLASSES_FILE+" "+GROUPS_FILE);
        } catch(IOException ex){
            return new Response<>(false, OpCode.Failed_To_Read_Bytes);
        }

        String studentsRows[] = splitNewLine(studentsFile);
        String[] classesRows = classesFile.split("lb[0-9]");
        String teachersRows[] = splitNewLine(teacherFile);
        String groupsRows[] = splitNewLine(groupsFile);
        if (!checkCsvHeadersValidity(studentsRows, classesRows, teachersRows, groupsRows)) {
            return new Response<>(false, OpCode.Wrong_File_Headers);
        }
        Response<List<Classroom>> classesRes = createClassesFromCsv(studentsRows, classesRows);
        if (!classesRes.getReason().equals(OpCode.Success)){
            return new Response<>(false, classesRes.getReason());
        }
        List<Classroom> classes = classesRes.getValue();
        synchronized (ADD_USER_LOCK) {
            Response<Boolean> classRoomResp = classRoomRepo.saveAll(classes);
            if (classRoomResp.getValue()) {
                Response<List<Teacher>> teachersRes = createTeachersFromCsv(teachersRows, groupsRows, classes);
                if (!teachersRes.getReason().equals(OpCode.Success)){
                    return new Response<>(false, teachersRes.getReason());
                }
                List<Teacher> teachers = teachersRes.getValue();
                Response<Boolean> teacherResp = teacherRepo.saveAll(teachers);
                return teacherResp;
            }
            return classRoomResp;
        }

    }

    private boolean checkFilesExtension(MultipartFile[] CSVs){
        for (int i = 0 ; i < CSVs.length ; i++){
            if (!(CSVs[i].getContentType().equals("csv/text") || CSVs[i].getContentType().equals("text/csv")) ) {
                return false;
            }
        }
        return true;
    }

    private String getFileStringByName(MultipartFile[] CSVs, String name) throws IOException {
        Stream<MultipartFile> filesStream = Arrays.stream(CSVs).filter(x -> x.getOriginalFilename().equals(name));
        Optional<MultipartFile> fileOpt = filesStream.findFirst();
        MultipartFile file =  fileOpt.get();
        return new String(file.getBytes(),StandardCharsets.UTF_8);
    }

    private Response<List<Classroom>> createClassesFromCsv(String studentRows[], String[] classesRows){
        HashMap<String,Student> students = new HashMap<>();
        List<Classroom> classes = new ArrayList<>();
        for(int i = 1; i < studentRows.length; i++){
            String[] studentData = studentRows[i].split(COMMA_DEL);
            String alias = Utils.getAlias(studentData[EMAIL]);
            if (alias == null) {
                return new Response<>(null, OpCode.InvalidStudentMail, studentData[EMAIL]);
            }
            students.put(alias, new Student(alias, studentData[FIRST_NAME], studentData[LAST_NAME]));
        }
        for(int i = 1 ; i < classesRows.length; i ++){
            String[] classData = classesRows[i].split(COMMA_DEL);
            Pair<String, String> classPair = Utils.getYearAndClassFromEmail(classData[CLASS_EMAIL]);
            if(classPair == null) {
                return new Response<>(null, OpCode.InvalidClassMail, classData[CLASS_EMAIL]);
            }
            HashMap<String, Student> studentsInClass = getStudentInClass(classData[MEMBERS], students);

            classes.add(creatClass(studentsInClass, classPair));
        }
        return new Response<>(classes, OpCode.Success);
    }

    @Override
    protected Response<IT> checkIT(String apiKey){
        String alias = ram.getAlias(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<IT> ITResponse = itRepo.findITById(alias);
        return checkITResponse(ITResponse);
    }

    private Response<IT> checkITResponse(Response<IT> ITResponse){
        if(ITResponse.getReason()!= OpCode.Success){
            return new Response<>(null,ITResponse.getReason());
        }
        IT it = ITResponse.getValue();
        if(it == null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(it,OpCode.Success);
    }

    private HashMap<String, Student> getStudentInClass(String membersString, HashMap<String, Student> students) {
        String[] membersEmails = membersString.split(NEW_LINE_DEL);
        HashMap<String, Student> studentsInClass = new HashMap<>();
        for (int i = 0; i < membersEmails.length; i++){
            String alias = Utils.getAlias(membersEmails[i]);
            Student stud = students.get(alias);
            if ( stud != null){
                studentsInClass.put(alias, stud);
            }
        }
        return studentsInClass;
    }

    private String[] splitNewLine(String csv){
        return csv.split(NEW_LINE_DEL);
    }

    private Classroom creatClass(HashMap<String, Student> studentsInClass, Pair<String, String> className){
        ClassGroup tempGroup = new ClassGroup(UniqueStringGenerator.getTimeNameCode("GR-"+className+"C"), GroupType.C, studentsInClass);
        ClassGroup groupA = new ClassGroup(UniqueStringGenerator.getTimeNameCode("GR-"+className+"A"), GroupType.A, new HashMap<>());
        ClassGroup groupB = new ClassGroup(UniqueStringGenerator.getTimeNameCode("GR-"+className+"B"), GroupType.B, new HashMap<>());
        // ClassGroup[] classGroups = new ClassGroup[]{tempGroup,groupA,groupB};
        Classroom classRoom = new Classroom(className.toString(), new ArrayList<ClassGroup>(Arrays.asList(tempGroup,groupA,groupB)));
        return classRoom;
    }
    private Boolean checkCsvHeadersValidity(String[] studentsRows, String[] classesRows, String[] teachersRows, String[] groupsRows){
        if (!Utils.stringsArrayEquals(getHeaders(studentsRows), USERS_FILE_HEADERS) ||
            !Utils.stringsArrayEquals(getHeaders(classesRows), CLASSES_FILE_HEADERS) ||
            !Utils.stringsArrayEquals(getHeaders(teachersRows), USERS_FILE_HEADERS) ||
            !Utils.stringsArrayEquals(getHeaders(groupsRows), GROUPS_FILE_HEADERS)){
            return false;
        }
        return true;
    }
    private String[] getHeaders(String[] file){
        if(file.length > 0) {
            String[] headers = file[0].split((COMMA_DEL));
            stripHeaders(headers);
            return headers;
        }
        return new String[0];
    }
//First Name [Required]	Last Name [Required]	Email Address [Required]
    private void stripHeaders(String[] headers){
        for(int i =0 ; i <headers.length; i++) {
            headers[i] = headers[i].replace(" [Required]", "").replace("\r\n", "");
        }
    }

    private Response<List<Teacher>> createTeachersFromCsv(String[] teacherRows, String[] groupRows, List<Classroom> classes){
        List<Teacher> teachers = new ArrayList<>();
        Response<HashMap<String, Pair<String, GroupType>>> teacherNameToClassAndGroupRes = preProcessGroupsFile(groupRows);
        if (!teacherNameToClassAndGroupRes.getReason().equals(OpCode.Success)) {
            return new Response<>(null, teacherNameToClassAndGroupRes.getReason());
        }
        HashMap<String, Pair<String, GroupType>> teacherNameToClassAndGroup = teacherNameToClassAndGroupRes.getValue();

        for(int i = 1; i < teacherRows.length; i++){
            String[] teacherData = teacherRows[i].split(COMMA_DEL);
            String alias = Utils.getAlias(teacherData[EMAIL]);
            if (alias == null) {
                return new Response<>(null, OpCode.InvalidTeacherMail, teacherData[EMAIL]);
            }
            String fullName = teacherData[FIRST_NAME]+" "+teacherData[LAST_NAME];
            Pair<String, GroupType> classAndGroup = teacherNameToClassAndGroup.get(fullName);
            if(classAndGroup != null){
                Classroom classRoom = getClassRoomByName(classAndGroup.getKey(), classes);
                if (classRoom == null) {
                    return new Response<>(null, OpCode.ClassNotFound);
                }
                teachers.add(new Teacher(alias, teacherData[FIRST_NAME], teacherData[LAST_NAME],classRoom, classAndGroup.getValue() ));
            }
        }
        return new Response<>(teachers, OpCode.Success);
    }

    private Classroom getClassRoomByName(String className, List<Classroom> classes) {
        for( Classroom classRoom: classes){
            if (classRoom.getClassName().equals(className)){
                return classRoom;
            }
        }
        return null;
    }

    private Response<HashMap<String, Pair<String, GroupType>>> preProcessGroupsFile(String[] groupRows){
        HashMap<String, Pair<String, GroupType>> teacherNameToClass = new HashMap<>();
        for(int i =1; i<groupRows.length; i++){
            String[] groupData = groupRows[i].split(COMMA_DEL);
            String classRoomName = pareHebrewClassName(groupData[CLASS_NAME]);
            if (classRoomName == null) {
                return new Response<>(null, OpCode.InvalidClassName, groupData[CLASS_NAME]);
            }
            String teacher_A = groupData[GROUP_A_TEACHER];
            String teacher_B = groupData[GROUP_B_TEACHER];
            if (teacher_A.equals(teacher_B)){
                teacherNameToClass.put(teacher_A, new Pair<>(classRoomName, GroupType.BOTH));
            } else {
                teacherNameToClass.put(teacher_A, new Pair<>(classRoomName, GroupType.A));
                teacherNameToClass.put(teacher_B, new Pair<>(classRoomName, GroupType.B));
            }
        }
        return new Response<>(teacherNameToClass, OpCode.Success);
    }

    /**
     * return string representation of hebrew class name
     * example: י1 -> 72-1
     */
    private String pareHebrewClassName(String className) {
        String pattern = "^([^0-9][^0-9]?)([0-9]+)";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(className);
        if (m.matches()){
             String hebrewClass = m.group(1);
             String classNum = m.group(2);
             String numericClassName = getNumericStringFromHebrewClassName(hebrewClass);
             if( numericClassName != null){
                 return numericClassName+"="+classNum;
             }
        }
        return null;
    }

    private String getNumericStringFromHebrewClassName(String hebrewClass){
        switch (hebrewClass){
            case "י":
                return YUD;
            case "יא":
                return YUD_A;
            case "יב":
                return YUD_B;
            default:
                return null;
        }
    }
}
