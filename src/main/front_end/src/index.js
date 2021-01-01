import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { LoginScreen, RegisterScreen,AddDeterministicMissionScreen,
  ChooseMissionToAddScreen  } from './components/screens';

const Stack = createStackNavigator();

const App = () =>{
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="AddDeteministicMission">
        {<Stack.Screen name="AddDeteministicMission" component={AddDeterministicMissionScreen}/> }
        {<Stack.Screen name="AddMission" component={ChooseMissionToAddScreen}/> }
        {<Stack.Screen name="Register" component={RegisterScreen}/>}
        {<Stack.Screen name="Login" component={LoginScreen}/>}
      </Stack.Navigator>
    </NavigationContainer>
  )
}

export default App;
