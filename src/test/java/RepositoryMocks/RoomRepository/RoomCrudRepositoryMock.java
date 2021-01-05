package RepositoryMocks.RoomRepository;

import CrudRepositories.RoomCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.RoomType;
import missions.room.Domain.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;

import java.util.*;

public class RoomCrudRepositoryMock implements RoomCrudRepository {

    private DataGenerator dataGenerator;
    private Map<String,Room> rooms;

    public RoomCrudRepositoryMock(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
        rooms=new HashMap<>();
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
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Personal){
                return (StudentRoom)dataGenerator.getRoom(Data.Valid_Student);
            }
        }
        return null;
    }

    @Override
    public GroupRoom findGroupRoomForWriteByAlias(String groupName) {
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Group){
                return (GroupRoom)dataGenerator.getRoom(Data.Valid_Group);
            }
        }
        return null;
    }

    @Override
    public ClassroomRoom findClassroomRoomForWriteByAlias(String classroomName) {
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Class){
                return (ClassroomRoom)dataGenerator.getRoom(Data.Valid_Classroom);
            }
        }
        return null;
    }

    @Override
    public StudentRoom findStudentRoomByAlias(String alias) {
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Personal){
                return (StudentRoom)dataGenerator.getRoom(Data.Valid_Student);
            }
        }
        return null;
    }

    @Override
    public GroupRoom findGroupRoomByAlias(String groupName) {
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Group){
                return (GroupRoom)dataGenerator.getRoom(Data.Valid_Group);
            }
        }
        return null;
    }

    @Override
    public ClassroomRoom findClassroomRoomByAlias(String classroomName) {
        for(Room room:rooms.values()){
            if (room.getRoomTemplate().getType()== RoomType.Class){
                return (ClassroomRoom)dataGenerator.getRoom(Data.Valid_Classroom);
            }
        }
        return null;
    }

    @Override
    public <S extends Room> S save(S s) {
        if(s instanceof StudentRoom ) {
            rooms.put(s.getRoomId(),(S) dataGenerator.getRoom(Data.Valid_Student));
            return (S) dataGenerator.getRoom(Data.Valid_Student);
        }
        else if(s instanceof GroupRoom){
            rooms.put(s.getRoomId(),(S) dataGenerator.getRoom(Data.Valid_Group));
            return (S) dataGenerator.getRoom(Data.Valid_Group);
        }
        else{
            rooms.put(s.getRoomId(),(S) dataGenerator.getRoom(Data.Valid_Classroom));
            return (S) dataGenerator.getRoom(Data.Valid_Classroom);
        }
    }

    @Override
    public <S extends Room> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Room> findById(String s) {
        if(dataGenerator.getRoom(Data.Valid_Student).getRoomId()==s && rooms.containsKey(s)){
            return Optional.of(dataGenerator.getRoom(Data.Valid_Student));
        }
        if(dataGenerator.getRoom(Data.Valid_Group).getRoomId()==s && rooms.containsKey(s)){
            return Optional.of(dataGenerator.getRoom(Data.Valid_Group));
        }
        if(dataGenerator.getRoom(Data.Valid_Classroom).getRoomId()==s && rooms.containsKey(s)){
            return Optional.of(dataGenerator.getRoom(Data.Valid_Classroom));
        }

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
        rooms.remove(room.getRoomId());

    }

    @Override
    public void deleteAll(Iterable<? extends Room> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
