package Data;

import DataAPI.RegisterDetailsData;
import DataAPI.UserType;
import Domain.Student;

import java.util.HashMap;

public class DataGenerator {

    private HashMap<Data, Student> students;
    private HashMap<Data,RegisterDetailsData> registerDetailsDatas;

    public DataGenerator() {
        initStudents();
        initRegisterDetailsDatas();
    }

    private void initRegisterDetailsDatas() {
        registerDetailsDatas=new HashMap<Data, RegisterDetailsData>();
        registerDetailsDatas.put(Data.VALID,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.Student,"0541111111"));
        registerDetailsDatas.put(Data.NULL_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName",null, UserType.Student,"0541111111"));
        registerDetailsDatas.put(Data.EMPTY_PASSWORD,new RegisterDetailsData("NoAlasIsExistWithThatName","", UserType.Student,"0541111111"));
        registerDetailsDatas.put(Data.NULL_ALIAS,new RegisterDetailsData(null,"1234", UserType.Student,"0541111111"));
        registerDetailsDatas.put(Data.EMPTY_ALIAS,new RegisterDetailsData("","1234", UserType.Student,"0541111111"));
        registerDetailsDatas.put(Data.WRONG_TYPE,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.IT,"0541111111"));
        registerDetailsDatas.put(Data.NULL_PHONE,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.Student,null));
        registerDetailsDatas.put(Data.INVALID_PHONE,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.Student,"054111111"));
        registerDetailsDatas.put(Data.WRONG_PHONE,new RegisterDetailsData("NoAlasIsExistWithThatName","1234", UserType.Student,"0541111112"));
        registerDetailsDatas.put(Data.WRONG_NAME,new RegisterDetailsData("Wrong","1234", UserType.Student,"0541111111"));

    }

    private void initStudents() {
        students=new HashMap<Data, Student>();
        students.put(Data.VALID,new Student("NoAlasIsExistWithThatName","Yuval","Sabag","0541111111"));
    }

    public Student getStudent(Data data){
        return students.get(data);
    }

    public RegisterDetailsData getRegisterDetails(Data data) {
        return registerDetailsDatas.get(data);
    }
}
