const initialState = {
    name: '',
    minimalMissionsToPass: undefined,
    missionsToAdd:[],
    loading: false,
    type: 'Personal',
    allMissions:[],
    presentedMissions:[],
    notFilteredMission:[],
    errorMessage: '',
    apiKey:'',
    deterministic:true,
    story:true,
    openQuestion:true,
    trivia:true,
  };

  import {
    TEMPLATE_NAME_CHANGED,
    MINIMAL_MISSIONS_CHANGED,
    TYPE_CHANGED,
    MISSIONS_CHANGED,
    ADD_TEMPLATE,
    UPDATE_ERROR_TEMPLATE,
    CLEAR_STATE,
    LOGIN_TEACHER,
    GET_MISSIONS,
    PASS_TO_CHOOSE_MISSIONS,
    FILTER_MISSIONS,
    CHANGE_DETERMINISTIC_CHECKBOX,
    CHANGE_OPEN_QUESTION_CHECKBOX,
    CHANGE_TRIVIA_CHECKBOX,
    CHANGE_STORY_CHECKBOX,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case TEMPLATE_NAME_CHANGED:
            return { ...state, name: action.payload };
        case MINIMAL_MISSIONS_CHANGED:
            return { ...state, minimalMissionsToPass: action.payload };
        case TYPE_CHANGED:
            return { ...state, type: action.payload };
        case CHANGE_DETERMINISTIC_CHECKBOX:
            return { ...state, deterministic: !state.deterministic };
        case CHANGE_OPEN_QUESTION_CHECKBOX:
            return { ...state, openQuestion: !state.openQuestion };
        case CHANGE_STORY_CHECKBOX:
            return { ...state, story: !state.story };
        case CHANGE_TRIVIA_CHECKBOX:
            return { ...state, trivia: !state.trivia };
        case MISSIONS_CHANGED:
            return { ...state, missionsToAdd: action.payload };
        case ADD_TEMPLATE:
            return { ...state, loading: true };
        case GET_MISSIONS:
            return { ...state, allMissions: action.payload};
        case FILTER_MISSIONS:
            return {...state, presentedMissions:action.payload}
        case PASS_TO_CHOOSE_MISSIONS:
            return { ...state, presentedMissions: action.payload, notFilteredMission:action.payload,errorMessage:'' };
        case LOGIN_TEACHER:
                return { ...initialState, apiKey: action.payload, errorMessage:'' };
        case UPDATE_ERROR_TEMPLATE:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false };
        case CLEAR_STATE:
            return initialState
        
        default:
            return state;
    }
  };