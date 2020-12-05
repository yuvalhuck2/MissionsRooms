package RepositoryMocks;

import Data.DataGenerator;
import Repositories.StudentRepository;
import Domain.Student;

import java.util.Optional;

import Data.Data;

public class StudentRepositoryMock implements StudentRepository {
    private DataGenerator dataGenerator;

    public StudentRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public <S extends Student> S save(S s) {
        return (S) dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public <S extends Student> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Student> findById(String s) {
        return Optional.ofNullable(dataGenerator.getStudent(Data.VALID));
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
