package RepositoryMocks.RoomTemplateRepository;

import CrudRepositories.RoomTemplateCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.RoomTemplate;

import java.util.ArrayList;
import java.util.Optional;

public class RoomTemplateMockRepositorySearch implements RoomTemplateCrudRepository {

        private DataGenerator dataGenerator;
        private Data opcode;

        public RoomTemplateMockRepositorySearch(DataGenerator dataGenerator,Data opcode){
            this.dataGenerator=dataGenerator;
            this.opcode=opcode;
        }

    @Override
    public <S extends RoomTemplate> S save(S s) {
        return null;
    }

    @Override
    public <S extends RoomTemplate> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<RoomTemplate> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<RoomTemplate> findAll() {
         Iterable<RoomTemplate> roomTemplates=new ArrayList<>();
        if(this.opcode== Data.VALID){
            ((ArrayList<RoomTemplate>) roomTemplates).add(dataGenerator.getRoomTemplate(Data.VALID));
            return roomTemplates;
        }
        else if(this.opcode==Data.EMPTY){
            return roomTemplates;
        }
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
