package RepositoryMocks.TeacherRepository;

import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import Domain.Teacher;

import java.util.Optional;

public class TeacherCrudRepositoryMock implements TeacherCrudRepository {

    private DataGenerator dataGenerator;

    public TeacherCrudRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public Teacher findTeacherForWrite(String alias) {
        return null;
    }

    @Override
    public Teacher findTeacherForRead(String alias) {
        return null;
    }

    @Override
    public <S extends Teacher> S save(S s) {
        return null;
    }

    @Override
    public <S extends Teacher> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Teacher> findById(String s) {
        return Optional.ofNullable(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Teacher> findAll() {
        return null;
    }

    @Override
    public Iterable<Teacher> findAllById(Iterable<String> iterable) {
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
    public void delete(Teacher teacher) {

    }

    @Override
    public void deleteAll(Iterable<? extends Teacher> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
