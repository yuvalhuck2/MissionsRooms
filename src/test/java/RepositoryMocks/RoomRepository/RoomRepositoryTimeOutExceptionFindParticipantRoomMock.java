package RepositoryMocks.RoomRepository;

import CrudRepositories.RoomCrudRepository;
import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;

import javax.persistence.LockTimeoutException;
import java.util.Optional;

public class RoomRepositoryTimeOutExceptionFindParticipantRoomMock implements RoomCrudRepository {

    private DataGenerator dataGenerator;

    public RoomRepositoryTimeOutExceptionFindParticipantRoomMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public Room findRoomForWrite(String roomId) {
        return null;
    }

    @Override
    public Room findRoomForRead(String roomId) {
        return null;
    }

    @Override
    public StudentRoom findStudentRoomForWriteByAlias(String alias) {
        throw new LockTimeoutException();
    }

    @Override
    public GroupRoom findGroupRoomForWriteByAlias(String groupName) {
        throw new LockTimeoutException();
    }

    @Override
    public ClassroomRoom findClassroomRoomForWriteByAlias(String classroomName) {
        throw new LockTimeoutException();
    }

    @Override
    public <S extends Room> S save(S s) {
        if (s instanceof StudentRoom) {
            return (S) dataGenerator.getRoom(Data.Valid_Student);
        } else if (s instanceof GroupRoom) {
            return (S) dataGenerator.getRoom(Data.Valid_Group);
        } else {
            return (S) dataGenerator.getRoom(Data.Valid_Classroom);
        }
    }

    @Override
    public <S extends Room> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Room> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Room> findAll() {
        return null;
    }

    @Override
    public Iterable<Room> findAllById(Iterable<String> iterable) {
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
    public void delete(Room room) {

    }

    @Override
    public void deleteAll(Iterable<? extends Room> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
