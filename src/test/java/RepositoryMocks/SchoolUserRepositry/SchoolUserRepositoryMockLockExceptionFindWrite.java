package RepositoryMocks.SchoolUserRepositry;

import CrudRepositories.SchoolUserCrudRepository;
import Data.DataGenerator;
import missions.room.Domain.Users.SchoolUser;

import javax.persistence.LockTimeoutException;
import java.util.Optional;

public class SchoolUserRepositoryMockLockExceptionFindWrite implements SchoolUserCrudRepository {

    private final DataGenerator dataGenerator;

    public SchoolUserRepositoryMockLockExceptionFindWrite(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public SchoolUser findUserForWrite(String alias) {
            throw new LockTimeoutException();
    }

    @Override
    public <S extends SchoolUser> S save(S s) {
        return null;
    }

    @Override
    public <S extends SchoolUser> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<SchoolUser> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<SchoolUser> findAll() {
        return null;
    }

    @Override
    public Iterable<SchoolUser> findAllById(Iterable<String> iterable) {
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
    public void delete(SchoolUser schoolUser) {

    }

    @Override
    public void deleteAll(Iterable<? extends SchoolUser> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
