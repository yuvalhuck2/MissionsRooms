import {
    CLEAR_STATE,
    ENTER_CHAT_ROOM, INIT_DETEREMINISTIC, INIT_OPEN_QUESTION_MISSION, INIT_STORY_MISSION, LOGIN_STUDENT,
    LOGIN_TEACHER,
    PASS_TO_ROOMS,
    UPDATE_CHAT_ROOM,
    UPDATE_SEND_CHAT_ROOM
} from "../actions/types";
import {GiftedChat} from 'react-native-gifted-chat';
const initialState = {
    messagesProps:[],
    name:'',
    errorMessage: '',
    apiKey:'',
    loading: false,
    dialog:"",
    roomId:'',
};

export default (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_CHAT_ROOM:

            return {...state,messagesProps:[... state.messagesProps,action.payload]};
        case ENTER_CHAT_ROOM:
            var {name,apiKey,roomId}=action.payload;
            return {...state,name:name,apiKey:apiKey,roomId:roomId}

        case INIT_OPEN_QUESTION_MISSION:
            var {roomData,isIncharge}=action.payload;
            return {...state,name:roomData.studentName,roomId:roomData.roomId}
        case INIT_STORY_MISSION:
            var {roomData,isIncharge}=action.payload;
            return {...state,name:roomData.studentName,roomId:roomData.roomId}
        case INIT_DETEREMINISTIC:
            var {roomData,isIncharge}=action.payload;
            return {...state,name:roomData.studentName,roomId:roomData.roomId}

        case LOGIN_STUDENT:
            return {...initialState,apiKey: action.payload, errorMessage:''}
        case LOGIN_TEACHER:
            return {...initialState,apiKey: action.payload, errorMessage:''}
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
}