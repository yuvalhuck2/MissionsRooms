package DataAPI;

import java.util.List;

public class RoomsDataByRoomType {
    private RoomType roomType;
    private List<RoomDetailsData> roomDetailsDataList;

    public RoomsDataByRoomType(RoomType roomType, List<RoomDetailsData> roomDetailsDataList) {
        this.roomType = roomType;
        this.roomDetailsDataList = roomDetailsDataList;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public List<RoomDetailsData> getRoomDetailsDataList() {
        return roomDetailsDataList;
    }

    public void setRoomDetailsDataList(List<RoomDetailsData> roomDetailsDataList) {
        this.roomDetailsDataList = roomDetailsDataList;
    }
}
