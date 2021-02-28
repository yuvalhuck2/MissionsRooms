package RepositoryMocks.StudentRepositoryMock;

import CrudRepositories.StudentCrudRepository;
import Data.DataGenerator;
import missions.room.Domain.Users.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StudentRepositoryMock implements StudentCrudRepository {


    private DataGenerator dataGenrator;
    private Map<String,Student> studentMap;

    public StudentRepositoryMock(DataGenerator dataGenerator){
        this.dataGenrator=dataGenerator;
        studentMap=new HashMap<>();
    }

    @Override
    public Student findUserForWrite(String alias) {
        if(studentMap.containsKey(alias)){
            return studentMap.get(alias);
        }

        return null;
    }

    @Override
    public Student findUserForRead(String alias) {
        if(studentMap.containsKey(alias)){
            return studentMap.get(alias);
        }

        return null;
    }

    @Override
    public <S extends Student> S save(S s) {
        studentMap.put(s.getAlias(),s);

        return (S) studentMap.get(s.getAlias());
    }

    @Override
    public <S extends Student> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Student> findById(String s) {
        if(studentMap.containsKey(s)){
            return Optional.of(studentMap.get(s));
        }
        /*
        if(s.equals(dataGenrator.getStudent(Data.VALID).getAlias()))
            return Optional.of(dataGenrator.getStudent(Data.VALID));*/
        else return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Student> findAll() {
        return null;
    }

    @Override
    public Iterable<Student> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void deleteAll(Iterable<? extends Student> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
