package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.PasswordCodeAndTime;
import DataAPI.Response;
import Domain.Student;
import ExternalSystems.UniqueStringGenerator;
import ExternalSystems.VerificationCodeGenerator;
import Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginManager {


    @Autowired
    private StudentRepository studentRepository;

    //save verification codes and string for trace and clean old code
    private final ConcurrentHashMap<String, PasswordCodeAndTime> aliasToCode;

    public LoginManager(){
        this.aliasToCode=new ConcurrentHashMap<>();
    }

    /**
     * req 2.3 -login
     * @param alias - user alias
     * @param password - user password
     * @return API key if login succeeded
     */
    public Response<String> login (String alias, String password){
        if (!studentRepository.existsById(alias)){
            return new Response<>("user name does not exist", OpCode.Not_Exist);
        }
        Optional<Student> student=studentRepository.findById(alias);

        String api=UniqueStringGenerator.getUniqueCode(alias);
        return null;
    }
}
