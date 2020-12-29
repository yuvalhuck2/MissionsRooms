package RepositoryMocks.UserRepository;

import CrudRepositories.UserCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.User;

import java.util.Optional;


public class UserRepositoryMock implements UserCrudRepository {

    private DataGenerator dataGenerator;


    public UserRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;

    }

    @Override
    public User findUserForWrite(String alias) {
        if (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_IT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_IT);
        return null;
    }

    @Override
    public User findUserForRead(String alias) {
        if (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_STUDENT);
        if(dataGenerator.getUser(Data.VALID_IT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_IT);
        return null;
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
    public Optional<User> findById(String s) {
        if (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(s))
            return Optional.ofNullable(dataGenerator.getUser(Data.VALID_TEACHER));
        else if(dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(s))
            return Optional.ofNullable(dataGenerator.getUser(Data.VALID_TEACHER));
        return Optional.ofNullable(dataGenerator.getUser(Data.VALID_IT));
    }

    @Override
    public boolean existsById(String s) {
        return (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(s)||
                dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(s)||
                dataGenerator.getUser(Data.VALID_IT).getAlias().equals(s));
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
