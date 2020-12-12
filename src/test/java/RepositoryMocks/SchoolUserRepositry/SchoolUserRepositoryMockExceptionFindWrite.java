package RepositoryMocks.SchoolUserRepositry;

import CrudRepositories.SchoolUserCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.SchoolUser;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class SchoolUserRepositoryMockExceptionFindWrite implements SchoolUserCrudRepository {
    private DataGenerator dataGenerator;

    public SchoolUserRepositoryMockExceptionFindWrite(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public SchoolUser findUserForRead(String alias) {
        return dataGenerator.getStudent(Data.VALID);
    }

    @Override
    public SchoolUser findUserForWrite(String alias) {
        throw new EntityNotFoundException();
    }

    @Override
    public Optional<SchoolUser> findById(String s) {
        throw new EntityNotFoundException();
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
