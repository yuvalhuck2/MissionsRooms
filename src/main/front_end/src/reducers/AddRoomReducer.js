const initialState = {
    roomName: '',
    student:'',
    group:'',
    classroom:{},
    participantKey:'',
    bonus: '0',
    roomTemplate:'',
    loading: false,
    type: '',
    presentedTemplates:[],
    allTemplates:[],
    errorMessage: '',
    apiKey:'',
  };

  import {
    ROOM_NAME_CHANGED,
    BONUS_CHANGED,
    ADD_ROOM,
    GET_TEMPLATES,
    TEMPLATE_CHANGED,
    GROUP_CHANGED,
    STUDENT_CHANGED,
    UPDATE_ERROR_ROOM,
    GET_CLASSROOM,
    CLEAR_STATE,
    PASS,
    LOGIN_TEACHER,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case ROOM_NAME_CHANGED:
            return { ...state, roomName: action.payload };
        case BONUS_CHANGED:
            return { ...state, bonus: action.payload };
        case TEMPLATE_CHANGED:
            return { ...state, roomTemplate: action.payload };
        case GROUP_CHANGED:
            return { ...state, group: action.payload };
        case STUDENT_CHANGED:
            return { ...state, student: action.payload };
        case GET_TEMPLATES:
            return { ...state, allTemplates: action.payload};
        case GET_CLASSROOM:
            return {...state, classroom:action.payload};
        case PASS:
            return { ...state, participantKey: action.payload.participant,
                type: action.payload.roomType, errorMessage: '', presentedTemplates:action.payload.presentedTemplates };
        case ADD_ROOM:
            return { ...state, loading: true };
        case LOGIN_TEACHER:
            return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case UPDATE_ERROR_ROOM:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false };
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };