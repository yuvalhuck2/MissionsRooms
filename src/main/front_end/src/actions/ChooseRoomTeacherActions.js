import {
    CLASSROOM,
    CLEAR_STATE,
    GROUP,
    LOGIN_STUDENT,
    LOGIN_TEACHER, PASS,
    PASS_TO_ROOMS, PERSONAL, TEMPLATE_CHANGED,
    ROOM_CHANGED, PASS_TO_ROOM_MENU, UPDATE_ERROR_WATCH_SUGGESTIONS, UPDATE_ALL_SUGGESTIONS,UPDATE_ERROR_CLOSE_ROOM,UPDATE_CLOSE_ROOM
} from "./types";
import {closeSocket} from "../handler/WebSocketHandler";
import * as NavPaths from "../navigation/NavPaths";
import API from "../api/API";
import * as APIPaths from "../api/APIPaths";
import {Not_Exist, Success,Wrong_Key,DB_Error,CONNECTED_STUDENTS} from "./OpCodeTypes";
import {CloseRoom, GeneralErrors} from "../locale/locale_heb";
const {
    room_closed,
    connected_students
} = CloseRoom
const {
    server_error,
    teacher_not_exists_error,
    room_error,
}=GeneralErrors

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


export const closeRoom = ({navigation,apiKey,currentRoom}) => {
    return async (dispatch)=> {
        dispatch({ type: LOGIN_TEACHER, payload: apiKey });
        try {
            const res = await API.post(APIPaths.closeRoom, { apiKey,roomId:currentRoom.roomId });
            if (res) {
                checkCloseRoomResponse({
                    data: res.data,
                    dispatch,
                    navigation,
                });
            } else {
                dispatch({ type: UPDATE_ERROR_CLOSE_ROOM, payload: server_error });
            }
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_CLOSE_ROOM, payload: server_error });
        }
    };
}

const checkCloseRoomResponse = ({data, dispatch, navigation}) => {
    const { reason, value } = data;
    switch (reason) {
        case Wrong_Key:
            return dispatch({
                type: UPDATE_ERROR_CLOSE_ROOM,
                payload: teacher_not_exists_error,
            });
        case DB_Error:
            return dispatch({
                type: UPDATE_ERROR_CLOSE_ROOM,
                payload:room_error,
            });
        case CONNECTED_STUDENTS:
            alert(connected_students);
            return dispatch({
                type:UPDATE_ERROR_CLOSE_ROOM,
                payload:connected_students,
            })
        case Success:
            dispatch({ type: UPDATE_CLOSE_ROOM});
            alert(room_closed);
            return navigation.navigate(NavPaths.teacherMainScreen);
        default:
            return dispatch({ type: UPDATE_ERROR_CLOSE_ROOM, payload: server_error });
    }
}



