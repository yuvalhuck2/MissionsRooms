import {CLEAR_STATE, ENTER_CHAT_ROOM, LOGIN_TEACHER, UPDATE_CHAT_ROOM} from "../actions/types";

const initialState = {
    messages:[],
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
            let newMessages=this.messages.concat(action.payload)
            return {...state,messages:newMessages};
        case ENTER_CHAT_ROOM:
            return {...state,name:action.payload}
        case LOGIN_TEACHER:
            return {...state,apiKey: action.payload}
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
}