import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { LoginScreen, RegisterScreen,AddDeterministicMissionScreen,
  ChooseMissionToAddScreen,AddRoomTemplateScreen,ChooseMissionsForTemplateScreen  } from './components/screens';

const Stack = createStackNavigator();

const App = () =>{
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Register">
        {<Stack.Screen name="AddDeteministicMission" component={AddDeterministicMissionScreen}/> }
        {<Stack.Screen name="AddMission" component={ChooseMissionToAddScreen}/> }
        {<Stack.Screen name="ChooseMissionsForTemplate" component={ChooseMissionsForTemplateScreen}/> }
        {<Stack.Screen name="AddTemplate" component={AddRoomTemplateScreen}/> }
        {<Stack.Screen name="Register" component={RegisterScreen}/>}
        {<Stack.Screen name="Login" component={LoginScreen}/>}
      </Stack.Navigator>
    </NavigationContainer>
  )
}

export default App;
