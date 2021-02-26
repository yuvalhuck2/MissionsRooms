package RepositoryMocks.ITRepository;

import CrudRepositories.ITCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Users.IT;

import java.util.Optional;


public class ITRepositoryMock implements ITCrudRepository {

    private DataGenerator dataGenerator;

    public ITRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public <S extends IT> S save(S s) {
        return (S) this.dataGenerator.getUser(Data.VALID_IT);
    }

    @Override
    public <S extends IT> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<IT> findById(String s) {
        return  Optional.ofNullable((IT)dataGenerator.getUser(Data.VALID_IT));
    }

    @Override
    public boolean existsById(String s) {

        return dataGenerator.getUser(Data.VALID_IT).getAlias().equals(s);
    }

    @Override
    public Iterable<IT> findAll() {
        return null;
    }

    @Override
    public Iterable<IT> findAllById(Iterable<String> iterable) {
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
    public void delete(IT it) {

    }

    @Override
    public void deleteAll(Iterable<? extends IT> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
