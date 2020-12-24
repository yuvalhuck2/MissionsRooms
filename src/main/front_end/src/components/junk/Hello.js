import React, { useState } from 'react';
import { Button, StyleSheet, Text, View } from 'react-native';
import SayHelloApi from '../../api/SayHelloApi';
import axios from 'axios'

const Hello = () => {
  const [hello, setHello] = useState('Nothing');

  const sayHelloPress = () => {
    axios.get('https://localhost:8443/hello')
      .then((res) => {
        setHello(res.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <View>
      <Text>Space</Text>
      <Text>Space</Text>
      <Text>Space</Text>
      <Text>Space</Text>
      <Text>Space</Text>
      <Button title='Say Hello' onPress={sayHelloPress} />
      <Text>Server Says {hello}</Text>
    </View>
  );

  const styles = StyleSheet.create({});
};

export default Hello;
