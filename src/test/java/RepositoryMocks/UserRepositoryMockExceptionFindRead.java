package RepositoryMocks;

import CrudRepositories.UserCrudRepository;
import Data.Data;
import Data.DataGenerator;
import Domain.User;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class UserRepositoryMockExceptionFindRead implements UserCrudRepository {

    private DataGenerator dataGenerator;

    public UserRepositoryMockExceptionFindRead(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public User findUserForWrite(String alias) {
        return dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public User findUserForRead(String alias) {
        throw new EntityNotFoundException();
    }

    @Override
    public Optional<User> findById(String s) {
        throw new EntityNotFoundException();
    }

    @Override
    public <S extends User> S save(S s) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
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
