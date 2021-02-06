package RepositoryMocks.MissionRepository;

import CrudRepositories.MissionCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Mission;
import sun.awt.util.IdentityArrayList;

import java.util.*;

public class MissionCrudRepositoryMock implements MissionCrudRepository {
    private final DataGenerator dataGenerator;

    public MissionCrudRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator=dataGenerator;
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
        return (S) dataGenerator.getMission(Data.Valid_Deterministic);
    }

    @Override
    public <S extends Mission> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Mission> findById(String s) {
        return Optional.ofNullable(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Mission> findAll() {
        List<Mission> list=new ArrayList<>();
        list.add(dataGenerator.getMission(Data.Valid_Deterministic));
        Iterable<Mission> missions= list;
        return missions;
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
