package RepositoryMocks.TeacherRepository;

import CrudRepositories.TeacherCrudRepository;
import missions.room.Domain.Users.Teacher;

import javax.persistence.LockTimeoutException;
import java.util.List;
import java.util.Optional;

public class TeacherCrudRepositoryMockExceptionTimeoutFindByIdFor implements TeacherCrudRepository {
    @Override
    public Teacher findTeacherForWrite(String alias) {
        return null;
    }

    @Override
    public Teacher findTeacherForRead(String alias) {
        throw new LockTimeoutException();
    }

    @Override
    public List<Teacher> findTeacherByStudent(String student) {
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


        throw new LockTimeoutException();
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
