package RepositoryMocks.MissionRepository;

import CrudRepositories.MissionCrudRepository;
import Data.DataGenerator;
import missions.room.Domain.Mission;

import java.util.ArrayList;
import java.util.Optional;

public class MissionCrudRepositoryMockNoMissions implements MissionCrudRepository {
    private final DataGenerator dataGenerator;

    public MissionCrudRepositoryMockNoMissions(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

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
        return null;
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
        return new ArrayList<>();
    }

    @Override
    public Iterable<Mission> findAllById(Iterable<String> iterable) {
        return new ArrayList<>();
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
