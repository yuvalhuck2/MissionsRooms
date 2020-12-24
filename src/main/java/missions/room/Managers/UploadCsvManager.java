package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.Response;
import ExternalSystems.UniqueStringGenerator;
import Utils.Utils;
import javafx.util.Pair;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import missions.room.Domain.GroupType;
import missions.room.Domain.Student;
import missions.room.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.rmi.CORBA.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UploadCsvManager {
    private static final String TEACHER_FILE = "teachers.csv";
    private static final String STUDENT_FILE = "students.csv";
    private static final int FIRST_NAME = 0;
    private static final int LAST_NAME = 1;
    private static final int EMAIL = 2;

    private static final String CLASSES_FILE = "classes.csv";
    private static final int CLASS_EMAIL = 0;
    private static final int MEMBERS_COUNT = 1;
    private static final int MEMBERS = 2;

    private static final String NEW_LINE_DEL = "\\r?\\n";
    private static final String COMMA_DEL =",";

    @Autowired
    private UserRepo userRepo;

    ////
    public Response<Boolean> uploadCsv(String token, MultipartFile[] CSVs) throws IOException {
        // TODO: check token belongs to IT
        String studentsFile, classesFile, teacherFile;
        try{
            studentsFile = getFileStringByName(CSVs, STUDENT_FILE);
            classesFile = getFileStringByName(CSVs, CLASSES_FILE);
            //teacherFile = getFileStringByName(CSVs, TEACHER_FILE);
        } catch(NoSuchElementException ex){
            return new Response<Boolean>(false, OpCode.Wrong_File_Name, "expected file names: -- given file names: --");
        } catch(IOException ex){
            return new Response<Boolean>(false, OpCode.Failed_To_Read_Bytes);
        }

        String studentsRows[] = splitNewLine(studentsFile);
        String[] classesRows = classesFile.split("lb");
        //String teachersRows[] = splitNewLine(teacherFile);
        List<Classroom> classes = createClassesFromCsv(studentsRows, classesRows);

        return null;
    }

    public String getFileStringByName(MultipartFile[] CSVs, String name) throws IOException {
        Stream<MultipartFile> filesStream = Arrays.stream(CSVs).filter(x -> x.getOriginalFilename().equals(name));
        Optional<MultipartFile> fileOpt = filesStream.findFirst();
        MultipartFile file =  fileOpt.get();
        return new String(file.getBytes(),StandardCharsets.UTF_8);
    }

    public Boolean insertTeachers(String[] rows){
        return true;
    }

    public List<Classroom> createClassesFromCsv(String studentRows[], String[] classesRows){
        HashMap<String,Student> students = new HashMap<>();
        List<Classroom> classes = new ArrayList<>();
        for(int i = 1; i < studentRows.length; i++){
            String[] studentData = studentRows[i].split(COMMA_DEL);
            String alias = Utils.getAlias(studentData[EMAIL]);
            students.put(alias, new Student(alias, studentData[FIRST_NAME], studentData[LAST_NAME]));
        }
        for(int i = 1 ; i < classesRows.length; i ++){
            String[] classData = classesRows[i].split(COMMA_DEL);
            Pair<String, String> classPair = Utils.getYearAndClassFromEmail(classData[CLASS_EMAIL]);
            HashMap<String, Student> studentsInClass = getStudentInClass(classData[MEMBERS], students);
            //TODO: check compatible with members count
            classes.add(creatClass(studentsInClass, classPair));
        }

        return classes;
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
        ClassGroup tempGroup = new ClassGroup(UniqueStringGenerator.getTimeNameCode("GR"), GroupType.C, studentsInClass);
        Classroom classRoom = new Classroom(className.toString(), new ArrayList<ClassGroup>(Arrays.asList(tempGroup)));
        return classRoom;
    }
    private Boolean checkCsvValidity(){
        //TODO
        return true;
    }

}
