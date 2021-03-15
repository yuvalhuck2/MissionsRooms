import {
    CLASSROOM,
    CLEAR_STATE,
    GROUP,
    LOGIN_STUDENT,
    LOGIN_TEACHER, PASS,
    PASS_TO_ROOMS, PERSONAL, TEMPLATE_CHANGED,
    ROOM_CHANGED,PASS_TO_ROOM_MENU
} from "./types";
import {closeSocket} from "../handler/WebSocketHandler";
import * as NavPaths from "../navigation/NavPaths";


export const passToRoomList = ({ navigation, apiKey,roomsType,type}) => {
    return async (dispatch) => {
        dispatch({
            type: PASS_TO_ROOMS,
            payload:roomsType.filter((roomType) => roomType.roomType.toString()== type)[0],
        });
        navigation.navigate(NavPaths.chooseClassroomRoom);
    }
};

export const roomChanged = (room) => {
    return {
        type: ROOM_CHANGED,
        payload: room,
    };
};

export const passToRoomMenu = ({currentRoom,navigation,apiKey})=> {
    return async (dispatch) => {
        dispatch({
            type: PASS_TO_ROOM_MENU,
            payload:currentRoom,
        });
        navigation.navigate(NavPaths.teacherRoomMenu);
    }
}

