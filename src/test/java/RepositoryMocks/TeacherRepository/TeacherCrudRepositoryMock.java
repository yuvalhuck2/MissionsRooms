package RepositoryMocks.TeacherRepository;

import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Users.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static Data.Data.VALID_WITH_CLASSROOM;

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
        return dataGenerator.getTeacher(Data.VALID_WITH_GROUP_C);
    }

    @Override
    public List<Teacher> findTeacherByStudent(String student) {
        List<Teacher> teachers=new ArrayList<>();
        teachers.add(dataGenerator.getTeacher(VALID_WITH_CLASSROOM));
        return teachers;
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
        return Optional.ofNullable(dataGenerator.getTeacher(VALID_WITH_CLASSROOM));
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

    @Override
    public List<Teacher> findTeacherForWriteByClassroom(String classroomName) {
        return null;
    }
}
