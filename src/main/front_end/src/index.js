import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import React from "react";
import {
  AddDeterministicMissionScreen,
  AddITScreen,
  AddOpenQuestionMissionScreen,
  AddRoomScreen,
  AddRoomTemplateScreen,
  AddSuggestionScreen,
  AddTriviaMission,
  AddTriviaQuestion,
  AddTriviaSubject,
  AddUserScreen,
  AuthScreen,
  ChangePasswordScreen,
  ChatRoomScreen,
  ChooseClassroomRoomScreen,
  ChooseMissionsForTemplateScreen,
  ChooseMissionToAddScreen,
  ChooseStudentRoomScreen,
  ChooseTeacherRoomTypeScreen,
  ChooseTemplateScreen,
  CloseClassroomScreen,
  ITScreen,
  LoginScreen,
  ManageUsersScreen,
  PointsTableScreen,
  RegisterScreen,
  ResetPasswordScreen,
  SolveDeterministicScreen,
  SolveOpenQuestionMissionScreen,
  SolveStoryScreen,
  SolveTriviaMission,
  StudentScreen,
  TeacherScreen,
  Test,
  TriviaManagement,
  UploadCsvScreen,
  WatchAllOpenQuestionMissionsScreen,
  WatchMessagesScreen,
  WatchOpenAnswerSolutionScreen,
  WatchProfileScreen,
  WatchSuggestionsScreen,
} from "./components/screens";
import DeleteTriviaQuestion from "./components/screens/DeleteTriviaQuestion";
import TeacherRoomMenuScreen from "./components/screens/TeacherRoomMenuScreen";

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Register">
        <Stack.Screen name="CloseClassroom" component={CloseClassroomScreen} />
        <Stack.Screen name="ResetPassword" component={ResetPasswordScreen} />
        <Stack.Screen name="AddUser" component={AddUserScreen} />
        <Stack.Screen name="ManageUsers" component={ManageUsersScreen} />
        <Stack.Screen name="PointsTable" component={PointsTableScreen} />
        <Stack.Screen name="ChangePassword" component={ChangePasswordScreen} />
        <Stack.Screen name="Test" component={Test} />
        <Stack.Screen name="AddIT" component={AddITScreen} />
        <Stack.Screen
          name="SolveDeterministic"
          component={SolveDeterministicScreen}
        />
        <Stack.Screen name="SolveStory" component={SolveStoryScreen} />
        <Stack.Screen
          name="ChooseStudentRoom"
          component={ChooseStudentRoomScreen}
        />
        <Stack.Screen
          name="ChooseTemplateScreen"
          component={ChooseTemplateScreen}
        />
        <Stack.Screen
          name="AddDeteministicMission"
          component={AddDeterministicMissionScreen}
        />
        <Stack.Screen
          name="AddOpenQuestionMission"
          component={AddOpenQuestionMissionScreen}
        />
        <Stack.Screen
          name="WatchOpenAnswerSolutionScreen"
          component={WatchOpenAnswerSolutionScreen}
        />
        <Stack.Screen
          name="WatchAllOpenQuestionMissionsScreen"
          component={WatchAllOpenQuestionMissionsScreen}
        />
        <Stack.Screen
          name="SolveOpenQuestionMission"
          component={SolveOpenQuestionMissionScreen}
        />
        <Stack.Screen name="AddMission" component={ChooseMissionToAddScreen} />
        <Stack.Screen
          name="ChooseMissionsForTemplate"
          component={ChooseMissionsForTemplateScreen}
        />
        <Stack.Screen name="AddTemplate" component={AddRoomTemplateScreen} />
        <Stack.Screen name="Register" component={RegisterScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Auth" component={AuthScreen} />
        <Stack.Screen name="Teacher" component={TeacherScreen} />
        <Stack.Screen name="Student" component={StudentScreen} />
        <Stack.Screen name="AddRoom" component={AddRoomScreen} />
        <Stack.Screen name="IT" component={ITScreen} />
        <Stack.Screen name="UploadCsv" component={UploadCsvScreen} />
        <Stack.Screen name="WatchProfile" component={WatchProfileScreen} />
        <Stack.Screen name="WatchMessages" component={WatchMessagesScreen} />
        <Stack.Screen name="AddSuggestion" component={AddSuggestionScreen} />
        <Stack.Screen
          name="WatchSuggestions"
          component={WatchSuggestionsScreen}
        />
        <Stack.Screen
          name="ChooseTeacherRoomType"
          component={ChooseTeacherRoomTypeScreen}
        />
        <Stack.Screen
          name="ChooseClassroomRoom"
          component={ChooseClassroomRoomScreen}
        />
        <Stack.Screen
          name="TeacherRoomMenu"
          component={TeacherRoomMenuScreen}
        />
        <Stack.Screen name="ChatRoom" component={ChatRoomScreen} />
        <Stack.Screen name="TriviaManagement" component={TriviaManagement} />
        <Stack.Screen name="AddTriviaQuestion" component={AddTriviaQuestion} />
        <Stack.Screen name="AddTriviaMission" component={AddTriviaMission} />
        <Stack.Screen name="AddTriviaSubject" component={AddTriviaSubject} />
        <Stack.Screen
          name="DeleteTriviaQuestion"
          component={DeleteTriviaQuestion}
        />
        <Stack.Screen name="SolveTriviaMission" component={SolveTriviaMission} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
