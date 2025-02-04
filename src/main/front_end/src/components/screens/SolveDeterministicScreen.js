import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  enterChatStudent,
  handleBack,
} from "../../actions/ChooseStudentRoomActions";
import {
  answerChanged,
  sendDeterministicAnswer,
} from "../../actions/SolveDeterministicActions";
import { theme } from "../../core/theme";
import { SolveDeterministicMissionStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const { enter_answer, send_answer } = SolveDeterministicMissionStrings;

class SolveDeterministicForm extends Component {
  constructor(...args) {
    super(...args);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.onChatButtonPress = this.onChatButtonPress.bind(this);
  }

  onAnswerChanged(text) {
    this.props.answerChanged(text);
  }

  onButtonPress() {
    const {
      roomId,
      mission,
      tries,
      apiKey,
      navigation,
      currentAnswer,
    } = this.props;
    this.props.sendDeterministicAnswer({
      roomId,
      mission,
      tries,
      apiKey,
      navigation,
      currentAnswer,
    });
  }

  onChatButtonPress() {
    const { navigation } = this.props;
    this.props.enterChatStudent({ navigation });
  }

  onBackPress() {
    const { navigation, apiKey, roomId, mission } = this.props;
    this.props.handleBack({
      navigation,
      apiKey,
      roomId,
      missionId: mission.missionId,
    });
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
    const { loading, isInCharge } = this.props;

    return loading ? (
      this.renderSpinner()
    ) : isInCharge ? (
      <Button
        mode="contained"
        style={styles.button}
        onPress={this.onButtonPress}
      >
        {send_answer}
      </Button>
    ) : null;
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

  renderTextBox() {
    const { currentAnswer, isInCharge } = this.props;
    if (isInCharge) {
      return (
        <TextInput
          label={enter_answer}
          value={currentAnswer}
          onChangeText={this.onAnswerChanged}
          placeholder="תשובה"
        />
      );
    }
  }

  render() {
    const { mission } = this.props;
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar
          backAction={this.onBackPress}
          actions={[{ icon: "chat", onPress: this.onChatButtonPress }]}
        />
        <Header>{mission.question}</Header>
        {this.renderTextBox()}
        {this.renderButton()}
        {this.renderError()}
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 24,
  },
  row: {
    flexDirection: "row",
    marginTop: 10,
  },
  link: {
    fontWeight: "bold",
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
  chatButton: {
    //alignSelf: 'flex-end',
    position: "absolute",
    top: 550,
    right: 0,
  },
});

const mapStateToProps = (state) => {
  const {
    roomId,
    loading,
    mission,
    currentAnswer,
    apiKey,
    tries,
    isInCharge,
    errorMessage,
  } = state.SolveDeterministic;
  return {
    roomId,
    loading,
    mission,
    currentAnswer,
    apiKey,
    tries,
    isInCharge,
    errorMessage,
  };
};

export default connect(mapStateToProps, {
  answerChanged,
  sendDeterministicAnswer,
  handleBack,
  enterChatStudent,
})(SolveDeterministicForm);
