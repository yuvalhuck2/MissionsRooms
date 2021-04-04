import {CLEAR_STATE, ENTER_CHAT_ROOM, LOGIN_TEACHER, UPDATE_CHAT_ROOM, UPDATE_SEND_CHAT_ROOM} from "../actions/types";
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
            //let copyMsg=this.messagesProps;
            //copyMsg=copyMsg.push(action.payload)
            return {...state,messages:this.messagesProps.push(action.payload)};
        case ENTER_CHAT_ROOM:
            const {name,apiKey,roomId}=action.payload;
            return {...state,name:name,apiKey:apiKey,roomId:roomId}
            /*
        case UPDATE_SEND_CHAT_ROOM:
            //alert(action.payload.text);
            let newMessages1=[]
            return {...state,messages:GiftedChat.append(this.messages,[{
                    _id: 2,
                    text: 'Hello developer',
                    createdAt: new Date(),
                    user: {
                        _id: 2,
                        name: 'React Native',
                        avatar: 'https://placeimg.com/140/140/any'
                    }
                }])};*/
        case LOGIN_TEACHER:
            return {...state,apiKey: action.payload}
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
}