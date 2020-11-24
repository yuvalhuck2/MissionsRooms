import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { StyleSheet, Text, View, Button, Alert  } from 'react-native';

export default function App() {
  let onPressLearnMore = () => fetch('http://localhost:8080/hello', {
      method: 'GET'
  }).then((response) => response.json()).then((res) => {
    Alert.alert(res);
  }).catch((err) => Alert.alert(err.toString()));
  return (
    <View style={styles.container}>
      <Text>Open up App.js to start working on your app!</Text>
      <Button
          onPress={onPressLearnMore}
          title="Learn More"
          color="#841584"
      />
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
