import React from 'react';
import { Provider as PaperProvider } from 'react-native-paper';
import { Provider as StoreProvider } from 'react-redux';
import App from './src'
import store from './src/store/store'

// export default function App() {
//     let onPressLearnMore = () => fetch('https://localhost:8443/hello', {
//         method: 'GET'
//     }).then((response) => response.json()).then((res) => {
//         Alert.alert(res.toString());
//     }).catch((err) => Alert.alert(err.toString()+" fuc "));
//     return (
//         <View style={styles.container}>
//             <Text>Open up App.js to start working on your app!</Text>
//             <Button
//                 onPress={onPressLearnMore}
//                 title="Learn More"
//                 color="#841584"
//             />
//         </View>
//     );
// }

// const appbarActions = [
//   { icon: 'archive', onPress: () => console.log('pressed archive') },
// ];

// const store = createStore(() => [], {}, applyMiddleware());

// const App = () => {
//   const styles = {
//     location: {
//       position: 'absolute',
//       left: 0,
//       right: 0,
//       bottom: 0,
//     },
//   };

const Main = () => {

  return (
    <StoreProvider store={store}>
      <PaperProvider>
        <App />
      </PaperProvider>
    </StoreProvider>
  );
};

export default Main;
