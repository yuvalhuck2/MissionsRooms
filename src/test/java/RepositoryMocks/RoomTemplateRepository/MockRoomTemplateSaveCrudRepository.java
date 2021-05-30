package RepositoryMocks.RoomTemplateRepository;

import CrudRepositories.RoomTemplateCrudRepository;
import missions.room.Domain.RoomTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class MockRoomTemplateSaveCrudRepository implements RoomTemplateCrudRepository {

    @Override
    public <S extends RoomTemplate> S save(S s) {
        throw new EntityNotFoundException();
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
