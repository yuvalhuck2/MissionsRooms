package RepositoryMocks.MissionRepository;

import CrudRepositories.MissionCrudRepository;
import missions.room.Domain.Mission;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class MissionCrudRepositoryMockExeptionSave implements MissionCrudRepository {
    @Override
    public Mission findMissionForWrite(String missionId) {
        return null;
    }

    @Override
    public Mission findMissionForRead(String missionId) {
        return null;
    }

    @Override
    public <S extends Mission> S save(S s) {
        throw new EntityNotFoundException();
    }

    @Override
    public <S extends Mission> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Mission> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Mission> findAll() {
        return null;
    }

    @Override
    public Iterable<Mission> findAllById(Iterable<String> iterable) {
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
    public void delete(Mission mission) {

    }

    @Override
    public void deleteAll(Iterable<? extends Mission> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
