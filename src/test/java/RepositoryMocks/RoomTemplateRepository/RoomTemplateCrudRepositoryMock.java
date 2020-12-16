package RepositoryMocks.RoomTemplateRepository;

import CrudRepositories.RoomTemplateCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Mission;
import missions.room.Domain.RoomTemplate;

import java.util.Optional;

public class RoomTemplateCrudRepositoryMock implements RoomTemplateCrudRepository {

    private DataGenerator dataGenerator;

    public RoomTemplateCrudRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator=dataGenerator;
    }

    @Override
    public Mission findMissionForWrite(String roomTemplateId) {
        return null;
    }

    @Override
    public Mission findMissionForRead(String roomTemplateId) {
        return null;
    }

    @Override
    public <S extends RoomTemplate> S save(S s) {
        return (S) dataGenerator.getRoomTemplate(Data.VALID);
    }

    @Override
    public <S extends RoomTemplate> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<RoomTemplate> findById(String s) {
        return Optional.ofNullable(dataGenerator.getRoomTemplate(Data.VALID));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<RoomTemplate> findAll() {
        return null;
    }

    @Override
    public Iterable<RoomTemplate> findAllById(Iterable<String> iterable) {
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
    public void delete(RoomTemplate roomTemplate) {

    }

    @Override
    public void deleteAll(Iterable<? extends RoomTemplate> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
