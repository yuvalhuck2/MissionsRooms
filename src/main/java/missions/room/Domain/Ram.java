package missions.room.Domain;

import DataAPI.ChatMessageData;
import DataAPI.OpCode;
import DataAPI.RecordTableData;
import DataAPI.Response;
import Utils.StringAndTime;
import missions.room.Domain.Rooms.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Ram {

    //save apikey and alias for trace and clean not connected users
    private static final ConcurrentHashMap<String, StringAndTime> apiToAlias = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Room> roomIdToRoom = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String,String> aliasToApi = new ConcurrentHashMap<>();
    private static final long TTL_OF_POINTS_TABLE = 2;

    private static RecordTable recordTableData = null;

    private static final ConcurrentHashMap<String, List<ChatMessageData>> roomToChat=new ConcurrentHashMap<>();

    public void addChatMessage(String roomId,ChatMessageData roomMessage){
        if(roomToChat.containsKey(roomId)) {
            roomToChat.get(roomId).add(roomMessage);
        }
        else{
            List<ChatMessageData> tmp=new ArrayList<>();
            tmp.add(roomMessage);
            roomToChat.put(roomId,tmp);
        }
    }

    public void addApi(String api,String alias){
        apiToAlias.put(api,new StringAndTime(alias));
    }

    public String getAlias(String apiKey) {
        if(apiToAlias.containsKey(apiKey)) {
            return apiToAlias.get(apiKey).getString();
        }
        return null;
    }

    public String getApiKey(String alias){
        return aliasToApi.get(alias);
    }

    public Room getRoom(String roomId){
        return roomIdToRoom.get(roomId);
    }

    public void deleteRoom(String roomId){
        roomIdToRoom.remove(roomId);
    }

    public boolean isRoomExist(String roomId){
        return roomIdToRoom.containsKey(roomId);
    }

    public void addAlias(String apiKey) {
        if(apiToAlias.containsKey(apiKey)) {
            aliasToApi.put(apiToAlias.get(apiKey)
                    .getString()
                    ,apiKey);
        }
    }

    public void removeApiKey(String apiKey) {
        if(apiToAlias.containsKey(apiKey)) {
            String alias = apiToAlias.get(apiKey).getString();
            aliasToApi.remove(alias);
        }
        apiToAlias.remove(apiKey);
    }

    /**
     * return if the student with alias @param alias is the mission in charge
     */
    public OpCode connectToRoom(String roomId, String alias){
        Room room=roomIdToRoom.get(roomId);
        if(room!=null) {
            synchronized (room) {
                return room.connect(alias);
            }
        }
        return OpCode.Not_Exist_Room;
    }

    /**
     * @return the mission in charge if the in charge was disconnected
     */
    public String disconnectFromRoom(String roomId,String alias){
        Room room=roomIdToRoom.get(roomId);
        if(room!=null) {
            synchronized (room) {
                Response<String> response=room.disconnect(alias);
                if(response.getReason()==OpCode.Delete){
                    roomIdToRoom.remove(roomId);
                }
                return response.getValue();
            }
        }
        return null;
    }

    public void addRoom(Room room) {
        roomIdToRoom.putIfAbsent(room.getRoomId(),room);
    }

    //cleaning for testing and maintenance
    public void clearRam() {
        clearRooms();
        apiToAlias.clear();
        aliasToApi.clear();
        roomIdToRoom.clear();
    }

    public void clearRooms() {
        roomIdToRoom.clear();
    }

    public String getMissionManager(String roomId) {
        Room room = getRoom(roomId);
        if(room != null) {
            return room.getMissionInCharge();
        }
        return null;
    }

    public RecordTable getRecordTable() {
        if(recordTableData == null || recordDataIsOld()){
            return null;
        }
        return recordTableData;
    }

    private boolean recordDataIsOld() {
        return recordTableData.getTimeStamp().compareTo(LocalDateTime.now().minusNanos(TTL_OF_POINTS_TABLE)) < 0;
    }

    public void updateTable(RecordTable tableData) {
        recordTableData=tableData;
        tableData.updateTimeStamp();
    }
}
