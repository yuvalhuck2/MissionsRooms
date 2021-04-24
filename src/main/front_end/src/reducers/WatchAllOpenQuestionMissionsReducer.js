const initialState = {
    apiKey: '',
    roomId: '',
    roomName: '',
    openAnswers: []
  };

  import {
    INIT_ALL_OPEN_ANS
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case INIT_ALL_OPEN_ANS:
            return {...state, roomId: action.payload.roomId, roomName: action.payload.roomName, openAnswers: action.payload.openAnswers, apiKey: action.payload.apiKey}
        default:
            return state;
    }
  };