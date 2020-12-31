package DataAPI;


//TODO implement needs to only the current mission and not all the missions
public class RoomDetailsData {
    private String roomId;
    private String name;
    private MissionData currentMission;

    public RoomDetailsData(String roomId,String name,MissionData currentMission){
        this.roomId=roomId;
        this.name=name;
        this.currentMission=currentMission;
    }
}
