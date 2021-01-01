const initialState = {
    name: '',
    minimalMissions: 0,
    missionsToAdd: [],
    loading: false,
    type: 'Personal',
    allMissions:[],
    presentedMissions:[],
  };

  import {
    NAME_CHANGED,
    MINIMAL_MISSIONS_CHANGED,
    TYPE_CHANGED,
    MISSIONS_CHANGED,
    ADD_TEMPLATE,
    UPDATE_ERROR,
    CLEAR_STATE,
    PASS,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case PASS:
            //navigation
            console.log('move to detemistic')
            return state;
        case NAME_CHANGED:
            console.log(action.payload)
            return { ...state, name: action.payload };
        case MINIMAL_MISSIONS_CHANGED:
            return { ...state, minimalMissions: action.payload };
        case TYPE_CHANGED:
            console.log(action.payload)
            return { ...state, type: action.payload };
        case MISSIONS_CHANGED:
            return { ...state, missionsToAdd: action.payload };
        case ADD_TEMPLATE:
            return { ...state, loading: true };
        case UPDATE_ERROR:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading: false };
        case CLEAR_STATE:
            return initialState;
        default:
            return state;
    }
  };