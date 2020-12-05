package Repositories;

import Domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    @Qualifier("fff")
    StudentRepository studentRepository;

    public Optional<Student> findById(String id){
        return studentRepository.findById(id);
    }

    public void save(Student student) {
        studentRepository.save(student);
    }
}
