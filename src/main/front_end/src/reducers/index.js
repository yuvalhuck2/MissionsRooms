import { combineReducers } from 'redux';
import addMissionReducer from './addMissionReducer';
import AuthReducer from './AuthReducer'
import addRoomTemplateReducer from './addRoomTemlateReducer'

export default combineReducers({
  auth: AuthReducer,
  addMission: addMissionReducer,
  addRoomTemplate: addRoomTemplateReducer
});
