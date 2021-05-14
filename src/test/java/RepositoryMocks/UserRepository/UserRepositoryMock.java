package RepositoryMocks.UserRepository;

import CrudRepositories.UserCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Users.BaseUser;

import java.util.Optional;


public class UserRepositoryMock implements UserCrudRepository {

    private DataGenerator dataGenerator;


    public UserRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;

    }

    @Override
    public BaseUser findUserForWrite(String alias) {
        if (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_IT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_IT);
        return null;
    }

    @Override
    public BaseUser findUserForRead(String alias) {
        if (dataGenerator.getUser(Data.VALID_TEACHER).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_TEACHER);
        else if(dataGenerator.getUser(Data.VALID_STUDENT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_STUDENT);
        if(dataGenerator.getUser(Data.VALID_IT).getAlias().equals(alias))
            return dataGenerator.getUser(Data.VALID_IT);
        return null;
    }

    @Override
    public <S extends BaseUser> S save(S s) {
        return null;
    }

    @Override
    public <S extends BaseUser> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<BaseUser> findById(String s) {
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
    public Iterable<BaseUser> findAll() {
        return null;
    }

    @Override
    public Iterable<BaseUser> findAllById(Iterable<String> iterable) {
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
    public void delete(BaseUser baseUser) {

    }

    @Override
    public void deleteAll(Iterable<? extends BaseUser> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
