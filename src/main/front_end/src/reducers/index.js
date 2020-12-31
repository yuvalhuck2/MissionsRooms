import { combineReducers } from 'redux';
import addMissionReducer from './addMissionReducer';
import AuthReducer from './AuthReducer'

export default combineReducers({
  auth: AuthReducer,
  addMission: addMissionReducer
});
