package RepositoryMocks.ClassroomRepository;

import CrudRepositories.ClassroomRepository;
import missions.room.Domain.Classroom;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Optional;

public class ClassRoomRepositoryExemptionSaveMock implements ClassroomRepository {


    public ClassRoomRepositoryExemptionSaveMock() {
    }

    @Override
    public <S extends Classroom> S save(S s) {
        throw new DataAccessResourceFailureException("s");
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
        return null;
    }
}
