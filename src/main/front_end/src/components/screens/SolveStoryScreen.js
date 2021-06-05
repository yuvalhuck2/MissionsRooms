import React, { Component } from "react";
import { StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  enterChatStudent,
  handleBack,
} from "../../actions/ChooseStudentRoomActions";
import {
  sendFinishStory,
  sendStoryAnswer,
  sentenceChanged,
} from "../../actions/SolveStoryActions";
import { theme } from "../../core/theme";
import { SolveStoryMissionStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
  header,
  enter_answer,
  send_answer,
  end_mission,
} = SolveStoryMissionStrings;

class SolveStoryFrom extends Component {
  constructor(...args) {
    super(...args);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.onFinishButtonPress = this.onFinishButtonPress.bind(this);
    this.onChatButtonPress = this.onChatButtonPress.bind(this);
  }

  onAnswerChanged(text) {
    this.props.sentenceChanged(text);
  }

  onButtonPress() {
    const { roomId, currentAnswer, apiKey } = this.props;
    this.props.sendStoryAnswer({ roomId, apiKey, currentAnswer });
  }

  onBackPress() {
    const { navigation, apiKey, roomId } = this.props;
    this.props.handleBack({ navigation, apiKey, roomId });
  }

  onFinishButtonPress() {
    const { apiKey, roomId } = this.props;
    this.props.sendFinishStory({ apiKey, roomId });
  }

  onChatButtonPress() {
    const { navigation } = this.props;
    this.props.enterChatStudent({ navigation });
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
          multiline={true}
          label={enter_answer}
          value={currentAnswer}
          onChangeText={this.onAnswerChanged}
        />
      );
    }
  }

  renderFinishButton() {
    const { isFinish } = this.props;
    return isFinish ? (
      <Button
        mode="contained"
        style={styles.button}
        onPress={this.onFinishButtonPress}
      >
        {end_mission}
      </Button>
    ) : null;
  }

  render() {
    const { story } = this.props;
    return (
      <SolveStoryScreen style={styles.container}>
        <CustomAppbar
          backAction={this.onBackPress}
          actions={[{ icon: "chat", onPress: this.onChatButtonPress }]}
        />
        <Header>{header}</Header>
        <KeyboardAwareScrollView style={styles.container}>
          <Text>{story} </Text>
        </KeyboardAwareScrollView>
        {this.renderTextBox()}
        {this.renderButton()}
        {this.renderFinishButton()}
        {this.renderError()}
      </SolveStoryScreen>
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
    story,
    currentAnswer,
    apiKey,
    isFinish,
    isInCharge,
    errorMessage,
  } = state.solveStory;
  return {
    roomId,
    loading,
    story,
    currentAnswer,
    apiKey,
    isFinish,
    isInCharge,
    errorMessage,
  };
};

export default connect(mapStateToProps, {
  sentenceChanged,
  sendStoryAnswer,
  sendFinishStory,
  handleBack,
  enterChatStudent,
})(SolveStoryFrom);
