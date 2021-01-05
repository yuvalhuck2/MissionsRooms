import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { LoginScreen, RegisterScreen,AddDeterministicMissionScreen,
  ChooseMissionToAddScreen,AddRoomTemplateScreen,ChooseMissionsForTemplateScreen,
    AuthScreen,TeacherScreen,AddRoomScreen,ChooseTemplateScreen,
    ChooseStudentRoomScreen,SolveDeterministicScreen,StudentScreen  } from './components/screens';

const Stack = createStackNavigator();

const App = () =>{
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Student">
        <Stack.Screen name="SolveDeterministic" component={SolveDeterministicScreen}/>
        <Stack.Screen name="ChooseStudentRoom" component={ChooseStudentRoomScreen}/>
        <Stack.Screen name="ChooseTemplateScreen" component={ChooseTemplateScreen}/>
        <Stack.Screen name="AddDeteministicMission" component={AddDeterministicMissionScreen}/> 
        <Stack.Screen name="AddMission" component={ChooseMissionToAddScreen}/> 
        <Stack.Screen name="ChooseMissionsForTemplate" component={ChooseMissionsForTemplateScreen}/> 
        <Stack.Screen name="AddTemplate" component={AddRoomTemplateScreen}/> 
        <Stack.Screen name="Register" component={RegisterScreen}/>
        <Stack.Screen name="Login" component={LoginScreen}/>
        <Stack.Screen name="Auth" component={AuthScreen}/>
        <Stack.Screen name="Teacher" component={TeacherScreen}/>
        <Stack.Screen name="Student" component={StudentScreen}/>
        <Stack.Screen name="AddRoom" component={AddRoomScreen}/>
      </Stack.Navigator>
    </NavigationContainer>
  )
}

export default App;
