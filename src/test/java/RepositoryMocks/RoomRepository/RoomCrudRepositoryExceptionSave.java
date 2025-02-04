package RepositoryMocks.RoomRepository;

import CrudRepositories.RoomCrudRepository;
import Data.DataGenerator;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.RoomOpenAnswersView;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.List;
import java.util.Optional;

public class RoomCrudRepositoryExceptionSave implements RoomCrudRepository {

    private DataGenerator dataGenerator;

    public RoomCrudRepositoryExceptionSave(DataGenerator dataGenerator) {
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
        return null;
    }

    @Override
    public GroupRoom findGroupRoomForWriteByAlias(String groupName) {
        return null;
    }

    @Override
    public ClassroomRoom findClassroomRoomForWriteByAlias(String classroomName) {
        return null;
    }

    @Override
    public RoomOpenAnswersView findAByTeacherAndId(String teacherAlias, String roomId) {
        return null;
    }

    @Override
    public StudentRoom findStudentRoomByAlias(String alias) {
        return null;
    }

    @Override
    public GroupRoom findGroupRoomByAlias(String groupName) {
        return null;
    }

    @Override
    public ClassroomRoom findClassroomRoomByAlias(String classroomName) {
        return null;
    }

    @Override
    public <S extends Room> S save(S s) {
        throw new DataAccessResourceFailureException("d");
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

    @Override
    public Iterable<Room> findRoomsForWriteByTeacherAlias(String teacherAlias) {
        return null;
    }
}
