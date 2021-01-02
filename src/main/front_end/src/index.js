import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { LoginScreen, RegisterScreen, UploadCsvScreen } from './components/screens';

const Stack = createStackNavigator();

const App = () =>{
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="UploadCsv">
        <Stack.Screen name="Register" component={RegisterScreen}/>
        <Stack.Screen name="Login" component={LoginScreen}/>
        <Stack.Screen name="UploadCsv" component={UploadCsvScreen}/>
      </Stack.Navigator>
    </NavigationContainer>
  )
}

export default App;
