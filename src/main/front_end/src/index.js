import { createAppContainer } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';
import { RegisterScreen } from './components/screens';
// import Home from './components/junk/Home'

const Router = createStackNavigator(
  {
    RegisterScreen,
  },
  {
    initialRouteName: 'RegisterScreen',
    headerMode: 'none',
  }
);

export default createAppContainer(Router);
