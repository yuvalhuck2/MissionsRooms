package RepositoryMocks.ClassGroupRepository;

import CrudRepositories.GroupRepository;
import Data.DataGenerator;
import missions.room.Domain.ClassGroup;

import java.util.Optional;

public class ClassGroupRepositoryMock implements GroupRepository {

    private DataGenerator dataGenerator;

    public ClassGroupRepositoryMock(DataGenerator dataGenerator){
        this.dataGenerator=dataGenerator;
    }

    @Override
    public <S extends ClassGroup> S save(S s) {
        return null;
    }

    @Override
    public <S extends ClassGroup> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<ClassGroup> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<ClassGroup> findAll() {
        return null;
    }

    @Override
    public Iterable<ClassGroup> findAllById(Iterable<String> iterable) {
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
    public void delete(ClassGroup classGroup) {

    }

    @Override
    public void deleteAll(Iterable<? extends ClassGroup> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
