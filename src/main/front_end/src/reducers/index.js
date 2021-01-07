import { combineReducers } from 'redux';
import addMissionReducer from './AddMissionReducer';
import AuthReducer from './AuthReducer'
import addRoomTemplateReducer from './AddRoomTemlateReducer'
import addRoomReducer from './AddRoomReducer'
import ChooseStudentRoom from './ChooseStudentRoomReducer'
import ITReducer from './ITReducer'

export default combineReducers({
  auth: AuthReducer,
  addMission: addMissionReducer,
  addRoomTemplate: addRoomTemplateReducer,
  addRoom: addRoomReducer,
  ChooseStudentRoom:ChooseStudentRoom,
  IT: ITReducer,
});
