import { combineReducers } from 'redux';
import addMissionReducer from './addMissionReducer';
import addRoomReducer from './AddRoomReducer';
import addRoomTemplateReducer from './addRoomTemlateReducer';
import AuthReducer from './AuthReducer';
import ChooseStudentRoom from './ChooseStudentRoomReducer';
import ITReducer from './ITReducer';

export default combineReducers({
  auth: AuthReducer,
  addMission: addMissionReducer,
  addRoomTemplate: addRoomTemplateReducer,
  addRoom: addRoomReducer,
  ChooseStudentRoom: ChooseStudentRoom,
  IT: ITReducer,
});
