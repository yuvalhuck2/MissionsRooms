import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import React from 'react';
import {
  AddDeterministicMissionScreen,
  AddRoomScreen,
  AddRoomTemplateScreen,
  AuthScreen,
  ChooseMissionsForTemplateScreen,
  ChooseMissionToAddScreen,
  ChooseStudentRoomScreen,
  ChooseTemplateScreen,
  ITScreen,
  LoginScreen,
  RegisterScreen,
  SolveDeterministicScreen,
  StudentScreen,
  TeacherScreen,
  UploadCsvScreen
} from './components/screens';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName='Login'>
        <Stack.Screen
          name='SolveDeterministic'
          component={SolveDeterministicScreen}
        />
        <Stack.Screen
          name='ChooseStudentRoom'
          component={ChooseStudentRoomScreen}
        />
        <Stack.Screen
          name='ChooseTemplateScreen'
          component={ChooseTemplateScreen}
        />
        <Stack.Screen
          name='AddDeteministicMission'
          component={AddDeterministicMissionScreen}
        />
        <Stack.Screen name='AddMission' component={ChooseMissionToAddScreen} />
        <Stack.Screen
          name='ChooseMissionsForTemplate'
          component={ChooseMissionsForTemplateScreen}
        />
        <Stack.Screen name='AddTemplate' component={AddRoomTemplateScreen} />
        <Stack.Screen name='Register' component={RegisterScreen} />
        <Stack.Screen name='Login' component={LoginScreen} />
        <Stack.Screen name='Auth' component={AuthScreen} />
        <Stack.Screen name='Teacher' component={TeacherScreen} />
        <Stack.Screen name='Student' component={StudentScreen} />
        <Stack.Screen name='AddRoom' component={AddRoomScreen} />
        <Stack.Screen name='IT' component={ITScreen} />
        <Stack.Screen name="UploadCsv" component={UploadCsvScreen}/>
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
