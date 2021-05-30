package DataObjects.APIObjects;

import DataObjects.FlatDataObjects.RoomDetailsData;
import DataObjects.FlatDataObjects.RoomType;

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

    public List<RoomDetailsData> getRoomDetailsDataList() {
        return roomDetailsDataList;
    }

}
