package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.Response;
import Utils.Utils;
import javafx.util.Pair;
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
        byte[] test = CSVs[0].getBytes();
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
        String classesRows[] = splitNewLine(classesFile);
        //String teachersRows[] = splitNewLine(teacherFile);
        List<Student> lst = createClassesFromCsv(studentsRows, classesRows);
        return null;
    }

    public String getFileStringByName(MultipartFile[] CSVs, String name) throws IOException {
        Stream<MultipartFile> filesStream = Arrays.stream(CSVs).filter(x -> x.getName().equals(name));
        Optional<MultipartFile> fileOpt = filesStream.findFirst();
        MultipartFile file =  fileOpt.get();
        return new String(file.getBytes(),StandardCharsets.UTF_8);
    }

    public Boolean insertTeachers(String[] rows){
        return true;
    }

    public List<Student> createClassesFromCsv(String studentRows[], String[] classesRows){
        HashMap<String,Student> students = new HashMap<>();
        for(int i = 1; i < studentRows.length; i++){
            String[] studentData = studentRows[i].split(COMMA_DEL);
            String alias = Utils.getAlias(studentData[EMAIL]);
            students.put(alias, new Student(alias, studentData[FIRST_NAME], studentData[LAST_NAME]));
        }
        for(int i =1; i < classesRows.length; i++){
            String[] classData = classesRows[i].split((COMMA_DEL));
            //Pair<String, String> classPair = Utils.getYearAndClassFromEmail(classData[CLASS_EMAIL]);


        }
        return null;
    }

    private String[] splitNewLine(String csv){
        return csv.split(NEW_LINE_DEL);
    }

    private Boolean checkCsvValidity(){
        //TODO
        return true;
    }

}
