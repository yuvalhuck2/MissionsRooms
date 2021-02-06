package DataAPI;


import missions.room.Domain.Room;

//TODO implement needs to only the current mission and not all the missions
public class RoomDetailsData {
    private String roomId;
    private String name;
    private MissionData currentMission;
    private RoomType roomType;

    public RoomDetailsData(String roomId, String name, MissionData currentMission, RoomType roomType){
        this.roomId=roomId;
        this.name=name;
        this.currentMission=currentMission;
        this.roomType=roomType;

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MissionData getCurrentMission() {
        return currentMission;
    }

    public void setCurrentMission(MissionData currentMission) {
        this.currentMission = currentMission;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
