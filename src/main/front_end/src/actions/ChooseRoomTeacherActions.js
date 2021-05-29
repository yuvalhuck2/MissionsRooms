import {
    CLASSROOM,
    CLEAR_STATE,
    GROUP,
    LOGIN_STUDENT,
    LOGIN_TEACHER, PASS,
    PASS_TO_ROOMS, PERSONAL, TEMPLATE_CHANGED,
    ROOM_CHANGED, PASS_TO_ROOM_MENU, UPDATE_ERROR_WATCH_SUGGESTIONS, UPDATE_ALL_SUGGESTIONS,
    UPDATE_ERROR_CLOSE_ROOM,UPDATE_CLOSE_ROOM,UPDATE_ERROR_CHAT_ROOM,ENTER_CHAT_ROOM,UPDATE_SEND_CHAT_ROOM,INIT_ALL_OPEN_ANS,
    UPDATE_ERROR_ANSWER_RESPONSE
} from "./types";
import {closeSocket} from "../handler/WebSocketHandler";
import * as NavPaths from "../navigation/NavPaths";
import API from "../api/API";
import * as APIPaths from "../api/APIPaths";
import {Not_Exist, Success, Wrong_Key, DB_Error, CONNECTED_STUDENTS, INVALID_ROOM_ID} from "./OpCodeTypes";
import {CloseRoom, GeneralErrors} from "../locale/locale_heb";
const {
    room_closed,
    connected_students,
    choose_room,
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
        if(currentRoom == ''){
            return alert(choose_room)
        }
        dispatch({
            type: PASS_TO_ROOM_MENU,
            payload:currentRoom,
        });
        navigation.navigate(NavPaths.teacherRoomMenu);

    }
}

export const sendMessage = ({navigation,newMessage,roomId,apiKey}) =>
{
    return async (dispatch)=>{
        //dispatch({type:LOGIN_TEACHER,payload:apiKey});
        try {
            const res = await API.post(APIPaths.sendChatMessage, {apiKey, newMessage, roomId});
            if (res) {
                checkSendMessageResponse({
                    data: res.data,
                    dispatch,
                    navigation,
                    newMessage
                });
            } else {
                dispatch({type: UPDATE_ERROR_CHAT_ROOM, payload: server_error});
            }
        }
        catch(err){
            return dispatch({type:UPDATE_ERROR_CHAT_ROOM,payload:server_error});
        }
    }
};

const checkSendMessageResponse=({data,dispatch,navigation,newMessage})=>{
    const { reason, value } = data;
    switch (reason) {
        case Success:
            //alert(newMessage.text)
            dispatch ({ type: UPDATE_SEND_CHAT_ROOM,payload:newMessage});
        default:
            return dispatch({ type: UPDATE_ERROR_CLOSE_ROOM, payload: server_error });
    }
};
/*

export const enterChatTeacher = ({navigation,apiKey,roomId})=> {
    return async (dispatch) => {
        dispatch({
            type: ENTER_CHAT_ROOM,
            payload:{name:'name',apiKey:apiKey,roomId:roomId},
        });
        navigation.navigate(NavPaths.chatRoom);
    }
}*/



export const enterChatTeacher = ({navigation,apiKey,roomId})=>{
    return async (dispatch)=>{
        //dispatch({type:LOGIN_TEACHER,payload:apiKey});
        try{
            const res=await API.post(APIPaths.enterChat,{apiKey,roomId});
            if(res){
                checkEnterChatResponse({
                    data:res.data,
                    dispatch,
                    navigation,
                    apiKey,
                    roomId,
                })
            } else {
                dispatch({type:UPDATE_ERROR_CHAT_ROOM,payload:server_error});
            }
        }
        catch (err) {
            return dispatch({type:UPDATE_ERROR_CHAT_ROOM,payload:server_error});

        }
    }

};

const checkEnterChatResponse=({data,dispatch,navigation,apiKey,roomId})=>{
    const {reason,value}=data;
    switch(reason){
        case Wrong_Key:
            return dispatch({
                type: UPDATE_ERROR_CHAT_ROOM,
                payload:teacher_not_exists_error
            });
        case DB_Error:
            return dispatch({
                type: UPDATE_ERROR_CHAT_ROOM,
                payload:teacher_not_exists_error,
            });
        case Success:
            dispatch({ type: ENTER_CHAT_ROOM,payload:{name:value,apiKey:apiKey,roomId:roomId}});
            return navigation.navigate(NavPaths.chatRoom);

    }
}



export const approveAnswer = ({navigation,apiKey,roomId,roomName})=>{
    return async (dispatch)=>{
        try{


            const res=await API.post(APIPaths.approveAnswer,{apiKey,roomId});

            if(res){

                checkApproveAnswerResponse({
                    data:res.data,
                    dispatch,
                    navigation,
                    apiKey,
                    roomId,
                    roomName,
                })
            } else {
                dispatch({type:UPDATE_ERROR_ANSWER_RESPONSE,payload:server_error});
            }
        }
        catch (err) {
            alert(err);
            return dispatch({type:UPDATE_ERROR_ANSWER_RESPONSE,payload:server_error});

        }
    }

};

const checkApproveAnswerResponse=({data,dispatch,navigation,apiKey,roomId,roomName})=>{
    const {reason,value}=data;
    switch(reason){
        case Wrong_Key:
            return dispatch({
                type: UPDATE_ERROR_ANSWER_RESPONSE,
                payload:teacher_not_exists_error
            });
        case INVALID_ROOM_ID:
            return dispatch({
                type: UPDATE_ERROR_ANSWER_RESPONSE,
                payload:room_error,
            });
        case DB_Error:
            return dispatch({
                type: UPDATE_ERROR_ANSWER_RESPONSE,
                payload:room_error,
            });
        case Success:

            dispatch({ type: INIT_ALL_OPEN_ANS,payload:{apiKey:apiKey,roomId:roomId,roomName:roomName,openAnswers: value.openAnswers}});
            return navigation.navigate(NavPaths.WatchAllOpenQuestionMissionsScreen);

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
};



