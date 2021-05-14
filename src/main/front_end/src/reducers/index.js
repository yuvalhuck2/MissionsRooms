import { combineReducers } from 'redux';
import addITReducer from './AddITReducer';
import addMissionReducer from './AddMissionReducer';
import addRoomReducer from './AddRoomReducer';
import addRoomTemplateReducer from './AddRoomTemlateReducer';
import AuthReducer from './AuthReducer';
import ChooseStudentRoom from './ChooseStudentRoomReducer';
import ITReducer from './ITReducer';
import SolveDeterministic from './SolveDeterministicReducer'
import AddSuggestionReducer from './AddSuggestionReducer';
import SolveStory from './SolveStoryReducer'
import WatchProfileReducer from './WatchProfileReducer'
import WatchMessagesReducer from './WatchMessagesReducer'
import WatchSuggestionsReducer from './WatchSuggestionReducer'
import ChangePasswordReducer from './ChangePasswordReducer';
import PointsTableReducer from './PointsTableReducer';
import ManageUsersReducer from './ManageUsersReducer';
import SolveOpenQuestion from './SolveOpenQuestionReducer'
import AddUser from './AddUserReducer'
import ResetPasswordReducer from './ResetPasswordReducer'
import ChatRoomReducer from './ChatRoomReducer';
import ChooseTeacherRoomsReducer from './ChooseTeacherRoomsReducer';
import WatchOpenAnswerReducer from './WatchOpenAnswerReducer'
import CloseClassroomReducer from './CloseClassroomReducer';
import WatchAllOpenQuestionMissionsReducer from './WatchAllOpenQuestionMissionsReducer';
import AddTriviaSubjectReducer from "./AddTriviaSubjectReducer"
import AddTriviaQuestionReducer from "./AddTriviaQuestionReducer";

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
  ChooseTeacherRoomType: ChooseTeacherRoomsReducer,
  ChatRoom: ChatRoomReducer,
  pointsTable: PointsTableReducer,
  manageUsers: ManageUsersReducer,
  SolveOpenQuestion: SolveOpenQuestion,
  addUser: AddUser,
  resetPassword: ResetPasswordReducer,
  WatchOpenAnswer: WatchOpenAnswerReducer,
  closeClassroom: CloseClassroomReducer,
  WatchAllOpenQuestionMissions: WatchAllOpenQuestionMissionsReducer,
  addTriviaSubject: AddTriviaSubjectReducer,
  addTriviaQuestion: AddTriviaQuestionReducer,
});
