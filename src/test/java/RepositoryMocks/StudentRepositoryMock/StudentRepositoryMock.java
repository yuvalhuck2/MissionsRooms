package RepositoryMocks.StudentRepositoryMock;

import CrudRepositories.StudentCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Student;

import java.util.Optional;

public class StudentRepositoryMock implements StudentCrudRepository {


    private DataGenerator dataGenrator;

    public StudentRepositoryMock(DataGenerator dataGenerator){
        this.dataGenrator=dataGenerator;
    }

    @Override
    public Student findUserForWrite(String alias) {
        return null;
    }

    @Override
    public Student findUserForRead(String alias) {
        return null;
    }

    @Override
    public <S extends Student> S save(S s) {

        return (S) dataGenrator.getStudent(Data.VALID);
    }

    @Override
    public <S extends Student> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Student> findById(String s) {
        if(s.equals(dataGenrator.getStudent(Data.VALID).getAlias()))
            return Optional.of(dataGenrator.getStudent(Data.VALID));
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
