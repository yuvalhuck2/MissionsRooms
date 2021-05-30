package RepositoryMocks.ClassroomRepository;

import CrudRepositories.ClassroomRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Classroom;

import java.util.List;
import java.util.Optional;

public class ClassRoomRepositoryMock implements ClassroomRepository {

    private DataGenerator dataGenerator;

    public ClassRoomRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public List<Classroom> findClassroomByGrade(String grade) {
        return null;
    }

    @Override
    public <S extends Classroom> S save(S s) {
        return (S) dataGenerator.getClassroom(Data.Valid_Classroom);
    }

    @Override
    public <S extends Classroom> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Classroom> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Classroom> findAll() {
        return null;
    }

    @Override
    public Iterable<Classroom> findAllById(Iterable<String> iterable) {
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
    public void delete(Classroom classroom) {

    }

    @Override
    public void deleteAll(Iterable<? extends Classroom> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Classroom findClassroomByStudent(String student) {

        return dataGenerator.getClassroom(Data.Valid_Classroom);
    }

    @Override
    public Classroom findClassroomForWrite(String className) {
        return null;
    }

    @Override
    public Iterable<String> getAllNames() {
        return null;
    }

    @Override
    public Classroom findClassroom(String className) {
        return null;
    }
}
