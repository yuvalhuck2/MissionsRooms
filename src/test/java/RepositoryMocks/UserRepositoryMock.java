package RepositoryMocks;

import Data.Data;
import Data.DataGenerator;

import Domain.User;
import CrudRepositories.UserCrudRepository;

import java.util.Optional;

public class UserRepositoryMock implements UserCrudRepository {
    private DataGenerator dataGenerator;

    public UserRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }


    @Override
    public User findUserForWrite(String alias) {
        return dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public User findUserForRead(String alias) {
        return dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public <S extends User> S save(S s) {
        return (S) dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(String s) {
        return Optional.ofNullable(dataGenerator.getStudent(Data.VALID));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> iterable) {
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
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
