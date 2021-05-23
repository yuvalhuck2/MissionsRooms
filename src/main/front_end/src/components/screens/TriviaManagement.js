import React, { Component } from 'react';
import { FlatList, StyleSheet, View } from 'react-native';
import { TriviaManagementStrings } from '../../locale/locale_heb';
import * as NavPaths from '../../navigation/NavPaths';
import Button from '../common/Button';
import Header from '../common/Header';
import { Appbar } from 'react-native-paper';

const {
  header,
  addSubjectTrivia,
  addQuestionTrivia,
  deleteQuestionTrivia,
  viewQuestions,
} = TriviaManagementStrings;

const navigateTo = (navigation, path) => {
  navigation.navigate(path);
};

const buttons = [
  {
    name: addSubjectTrivia,
    key: '0',
    onPress: (navigation) => {
      navigateTo(navigation, NavPaths.addTriviaSubject);
    },
  },
  {
    name: addQuestionTrivia,
    key: '1',
    onPress: (navigation) => {
      navigateTo(navigation, NavPaths.addTriviaQuestion);
    },
  },
  { name: deleteQuestionTrivia, key: '2', onPress: (navigation) => {} },
  { name: viewQuestions, key: '3', onPress: (navigation) => {} },
];

class TriviaManagement extends Component {
  constructor(...args) {
    super(...args);
    this.renderButtons = this.renderButtons.bind(this);
  }

  renderButtons() {
    const { navigation } = this.props;
    let renderItem = ({ item }) => {
      return (
        <Button
          mode='contained'
          style={styles.button}
          onPress={() => item.onPress(navigation)}
        >
          {item.name}
        </Button>
      );
    };
    return (
      <FlatList
        data={buttons}
        keyExtractor={(item) => item.key}
        renderItem={renderItem}
      />
    );
  }

  render() {
    return (
      <View style={styles.container}>
        <Appbar.Header styles={styles.bottom}>
          <Appbar.BackAction onPress={this.props.navigation.goBack} />
        </Appbar.Header>
        <Header>{header}</Header>
        {this.renderButtons()}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
  },
  button: {
    marginTop: 24,
  },
});

export default TriviaManagement;
