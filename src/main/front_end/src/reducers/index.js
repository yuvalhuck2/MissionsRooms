import { combineReducers } from "redux";
import addITReducer from "./AddITReducer";
import addMissionReducer from "./AddMissionReducer";
import addRoomReducer from "./AddRoomReducer";
import addRoomTemplateReducer from "./AddRoomTemlateReducer";
import AddSuggestionReducer from "./AddSuggestionReducer";
import AddTriviaQuestionReducer from "./AddTriviaQuestionReducer";
import AddTriviaSubjectReducer from "./AddTriviaSubjectReducer";
import AddUser from "./AddUserReducer";
import AuthReducer from "./AuthReducer";
import ChangePasswordReducer from "./ChangePasswordReducer";
import ChatRoomReducer from "./ChatRoomReducer";
import ChooseStudentRoom from "./ChooseStudentRoomReducer";
import ChooseTeacherRoomsReducer from "./ChooseTeacherRoomsReducer";
import CloseClassroomReducer from "./CloseClassroomReducer";
import DeleteTriviaQuestionReducer from "./DeleteTriviaQuestionReducer";
import ITReducer from "./ITReducer";
import ManageUsersReducer from "./ManageUsersReducer";
import PointsTableReducer from "./PointsTableReducer";
import ResetPasswordReducer from "./ResetPasswordReducer";
import SolveDeterministic from "./SolveDeterministicReducer";
import SolveOpenQuestion from "./SolveOpenQuestionReducer";
import SolveStory from "./SolveStoryReducer";
import SolveTriviaMissionReducer from "./SolveTriviaMissionReducer";
import WatchAllOpenQuestionMissionsReducer from "./WatchAllOpenQuestionMissionsReducer";
import WatchMessagesReducer from "./WatchMessagesReducer";
import WatchOpenAnswerReducer from "./WatchOpenAnswerReducer";
import WatchProfileReducer from "./WatchProfileReducer";
import WatchSuggestionsReducer from "./WatchSuggestionReducer";

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
  deleteTriviaQuestion: DeleteTriviaQuestionReducer,
  solveTriviaMission: SolveTriviaMissionReducer,
});
