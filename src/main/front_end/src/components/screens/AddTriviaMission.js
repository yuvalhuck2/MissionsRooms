import React, { Component } from "react";
import { FlatList, StyleSheet, View, Text } from "react-native";
import { Checkbox, ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  addMission,
  chooseTriviaQuestion,
  getTriviaQuestions,
  passRatioChanged,
  removeTriviaQuestion,
  typesChanged,
} from "../../actions/AddMissionActions";
import { TRIVIA } from "../../actions/types";
import { AddDeterministicMissionStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import Header from "../common/Header";
import TextInput from "../common/TextInput";
const { personal, group, classroom } = AddDeterministicMissionStrings;
import { theme } from '../../core/theme'

class AddTriviaMission extends Component {
  constructor(...args) {
    super(...args);
    this.renderQuestions = this.renderQuestions.bind(this);
    this.onPassRatioChanged = this.onPassRatioChanged.bind(this);
    this.onTypesChanged = this.onTypesChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.renderButton = this.renderButton.bind(this);
    this.renderError = this.renderError.bind(this);
  }

  componentDidMount() {
    this.props.getTriviaQuestions({ apiKey: this.props.apiKey });
  }

  onPassRatioChanged(text) {
    this.props.passRatioChanged(text);
  }

  onTypesChanged(text) {
    const { missionTypes } = this.props;
    let x;
    if (missionTypes.includes(text)) {
      x = missionTypes.filter((type) => type !== text);
    } else {
      x = [...missionTypes, text];
    }
    this.props.typesChanged(x);
  }

  onButtonPress() {
    const {
      apiKey,
      triviaQuestions,
      points,
      passRatio,
      missionTypes,
      navigation,
    } = this.props;

    this.props.addMission({
      apiKey,
      triviaQuestions,
      points,
      passRatio,
      navigation,
      missionTypes,
      className: TRIVIA,
    });
  }

  onQuestionChanged(item) {
    const {
      triviaQuestions,
      chooseTriviaQuestion,
      removeTriviaQuestion,
    } = this.props;
    triviaQuestions.includes(item)
      ? removeTriviaQuestion(item)
      : chooseTriviaQuestion(item);
  }

  renderQuestions() {
    const { triviaQuestions, allTriviaQuestions } = this.props;
    let renderItem = ({ item }) => {
      return (
        <View>
          <Checkbox.Item
            label={item.question}
            status={triviaQuestions.includes(item) ? "checked" : "unchecked"}
            onPress={() => this.onQuestionChanged(item)}
          />
        </View>
      );
    };

    return (
      <FlatList
        data={allTriviaQuestions}
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
      />
    );
  }

  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size="large"
      />
    );
  }

  renderButton() {
    const { loading } = this.props;

    return loading ? (
      this.renderSpinner()
    ) : (
      <Button
        mode="contained"
        style={styles.button}
        onPress={this.onButtonPress}
      >
        הוסף משימה
      </Button>
    );
  }

  renderError() {
    const { errorMessage } = this.props;

    if (errorMessage && errorMessage !== "") {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{errorMessage}</Text>
        </View>
      );
    }
  }

  renderRoomTypes() {
    const { missionTypes } = this.props;
    return (
      <>
        <Checkbox.Item
          label={personal}
          status={missionTypes.includes("Personal") ? "checked" : "unchecked"}
          onPress={() => this.onTypesChanged("Personal")}
        />

        <Checkbox.Item
          label={group}
          status={missionTypes.includes("Group") ? "checked" : "unchecked"}
          onPress={() => this.onTypesChanged("Group")}
        />

        <Checkbox.Item
          label={classroom}
          status={missionTypes.includes("Class") ? "checked" : "unchecked"}
          onPress={() => this.onTypesChanged("Class")}
        />
      </>
    );
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={{ flex: 1 }}>
          <Header>בחר שאלות עבור המשימה</Header>
          {this.renderQuestions()}
          <TextInput
            label="אחוז שאלות להצלחה"
            value={this.props.passRatio}
            onChangeText={this.onPassRatioChanged}
          />
        </View>

        <View style={{ flex: 1 }}>
          <Header headerStyle={{ marginTop: 0 }}>בחר סוגי משימה</Header>
          {this.renderRoomTypes()}
          {this.renderButton()}
          {this.renderError()}
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%",
  },
  button: {
    margin: 30,
    width: "80%",
  },
  errorTextStyle: {
    fontSize: 25,
    alignSelf: 'center',
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const {
    apiKey,
    triviaQuestions,
    passRatio,
    missionTypes,
    points,
    loading,
    errorMessage,
    allTriviaQuestions,
  } = state.addMission;
  return {
    apiKey,
    triviaQuestions,
    passRatio,
    missionTypes,
    points,
    loading,
    errorMessage,
    allTriviaQuestions,
  };
};

export default connect(mapStateToProps, {
  getTriviaQuestions,
  passRatioChanged,
  chooseTriviaQuestion,
  removeTriviaQuestion,
  addMission,
  typesChanged,
})(AddTriviaMission);
