import { combineReducers } from 'redux';
import addITReducer from './AddITReducer';
import addMissionReducer from './AddMissionReducer';
import addRoomReducer from './AddRoomReducer';
import addRoomTemplateReducer from './AddRoomTemlateReducer';
import AuthReducer from './AuthReducer';
import ChooseStudentRoom from './ChooseStudentRoomReducer';
import ITReducer from './ITReducer';
import SolveDeterministic from './SolveDeterministicReducer'
import SolveStory from './SolveStoryReducer'
import WatchProfileReducer from './WatchProfileReducer'
import WatchMessagesReducer from './WatchMessagesReducer'

export default combineReducers({
  auth: AuthReducer,
  addMission: addMissionReducer,
  addRoomTemplate: addRoomTemplateReducer,
  addRoom: addRoomReducer,
  ChooseStudentRoom: ChooseStudentRoom,
  IT: ITReducer,
  SolveDeterministic: SolveDeterministic,
  addIT: addITReducer,
  solveStory: SolveStory,
  WatchProfile: WatchProfileReducer,
  WatchMessages: WatchMessagesReducer,
});
