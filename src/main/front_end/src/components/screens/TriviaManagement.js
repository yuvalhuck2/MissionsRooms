import React, { Component } from "react";
import { FlatList, SafeAreaView, StyleSheet } from "react-native";
import { TriviaManagementStrings } from "../../locale/locale_heb";
import * as NavPaths from "../../navigation/NavPaths";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";

const {
  header,
  addSubjectTrivia,
  addQuestionTrivia,
  deleteQuestionTrivia,
} = TriviaManagementStrings;

const navigateTo = (navigation, path) => {
  navigation.navigate(path);
};

const buttons = [
  {
    name: addSubjectTrivia,
    key: "0",
    onPress: (navigation) => {
      navigateTo(navigation, NavPaths.addTriviaSubject);
    },
  },
  {
    name: addQuestionTrivia,
    key: "1",
    onPress: (navigation) => {
      navigateTo(navigation, NavPaths.addTriviaQuestion);
    },
  },
  {
    name: deleteQuestionTrivia,
    key: "2",
    onPress: (navigation) => {
      navigateTo(navigation, NavPaths.deleteTriviaQuestion);
    },
  },
];

class TriviaManagement extends Component {
  constructor(...args) {
    super(...args);
    this.renderButtons = this.renderButtons.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  renderButtons() {
    const { navigation } = this.props;
    let renderItem = ({ item }) => {
      return (
        <Button
          mode="contained"
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

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackAction} />
        <Header>{header}</Header>
        {this.renderButtons()}
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
  },
  button: {
    marginTop: 24,
  },
});

export default TriviaManagement;
