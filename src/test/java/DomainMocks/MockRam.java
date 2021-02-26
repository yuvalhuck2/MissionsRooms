package DomainMocks;

import Data.Data;
import Data.DataGenerator;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;

import java.util.concurrent.ConcurrentHashMap;

public class MockRam extends Ram {

    private final DataGenerator dataGenerator;
    private ConcurrentHashMap<String,Room> roomIdToRoom=new ConcurrentHashMap<>();

    public MockRam(DataGenerator dataGenerator) {
        this.dataGenerator=dataGenerator;
    }

    @Override
    public String getAlias(String apiKey) {
        if(apiKey.equals("key"))
            return dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getAlias();
        else if(apiKey.equals("apiKey")){
            return dataGenerator.getStudent(Data.VALID).getAlias();
        }
        return null;
    }

    @Override
    public boolean isRoomExist(String id){
        if(roomIdToRoom.containsKey(id)){

            return true;
        }
        return false;
        /*
        return dataGenerator.getRoom(Data.Valid_Classroom).getRoomId()==id||
                dataGenerator.getRoom(Data.Valid_Student).getRoomId()==id||
                dataGenerator.getRoom(Data.Valid_Group).getRoomId()==id;*/
    }

    @Override
    public Room getRoom(String roomId){
        if(roomIdToRoom.containsKey(roomId))
            return roomIdToRoom.get(roomId);
        return null;
        /*
        if(dataGenerator.getRoom(Data.Valid_Classroom).getRoomId()==roomId){
            return dataGenerator.getRoom(Data.Valid_Classroom);
        }
        if(dataGenerator.getRoom(Data.Valid_Student).getRoomId()==roomId){
            return dataGenerator.getRoom(Data.Valid_Student);
        }
        if(dataGenerator.getRoom(Data.Valid_Group).getRoomId()==roomId){
            return dataGenerator.getRoom(Data.Valid_Group);
        }
        return null;*/
    }

    @Override
    public void addRoom(Room room){
        roomIdToRoom.put(room.getRoomId(),room);
    }



    @Override
    public void deleteRoom(String roomId) {
        roomIdToRoom.remove(roomId);

    }
}
