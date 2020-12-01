import React from 'react';
import { StyleSheet, View } from 'react-native';
import Hello from './Components/Hello';

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

export default App = () => {
  return <View><Hello /></View>;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
