import { combineReducers } from 'redux';
import addITReducer from './addITReducer';
import addMissionReducer from './addMissionReducer';
import addRoomReducer from './AddRoomReducer';
import addRoomTemplateReducer from './addRoomTemlateReducer';
import AuthReducer from './AuthReducer';
import ChooseStudentRoom from './ChooseStudentRoomReducer';
import ITReducer from './ITReducer';
import AddSuggestionReducer from './AddSuggestionReducer';
import SolveDeterministic from './SolveDeterministicReducer'
import chooseTeacherRoomsReducer from './ChooseTeacherRoomsReducer'
import SolveStory from './SolveStoryReducer'
import WatchProfileReducer from './WatchProfileReducer'
import WatchMessagesReducer from './WatchMessagesReducer'
import WatchSuggestionsReducer from './WatchSuggestionReducer'
import ChangePasswordReducer from './ChangePasswordReducer';

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
  addSuggestion: AddSuggestionReducer,
  watchSuggestions: WatchSuggestionsReducer,
  changePassword: ChangePasswordReducer,
    ChooseTeacherRoomType: chooseTeacherRoomsReducer,
});

