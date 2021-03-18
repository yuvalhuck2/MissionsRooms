import React, { Component } from "react";
import { StyleSheet, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { RadioButton, ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import { answerChanged } from "../../actions/TriviaMissionActions";
import { theme } from "../../core/theme";
import Button from "../common/Button";
import Header from "../common/Header";

class TriviaMissionScreen extends Component {
  constructor(...args) {
    super(...args);
    this.renderRadioButtons = this.renderRadioButtons.bind(this);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
    this.renderTriviaQuestion = this.renderTriviaQuestion.bind(this);
    this.renderTriviaQuestions = this.renderTriviaQuestions.bind(this);
  }

  onAnswerChanged(questionId, answer) {
    this.props.answerChanged(questionId, answer);
  }

  renderRadioButtons(question) {
    let answerList = [];
    question.answers.map((answer) => {
      answerList.push(
        <RadioButton.Item
          key={answer}
          label={answer}
          value={answer}
          color={theme.colors.primary}
          labelStyle={{
            fontWeight: "bold",
            color: theme.colors.secondary,
          }}
        />
      );
    });
    return (
      <RadioButton.Group
        onValueChange={(value) =>
          this.onAnswerChanged(question.questionId, value)
        }
        value={question.currentAnswer}
      >
        {answerList}
      </RadioButton.Group>
    );
  }

  renderTriviaQuestion(question) {
    return (
      <View style={{ alignSelf: "center" }} key={question.questionId}>
        <Header>{question.question}</Header>
        {this.renderRadioButtons(question)}
      </View>
    );
  }

  renderTriviaQuestions(questions) {
    let questionsValues = Array.from(questions.values());
    return questionsValues.map((question) =>
      this.renderTriviaQuestion(question)
    );
  }

  // renderButton() {
  //   return (
  //     <Button mode="contained" style={styles.button} onPress={() => {}}>
  //       Submit Answer
  //     </Button>
  //   );
  // }


  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size='large'
      />
    );
  }

  renderError() {
    const { errorMessage } = this.props;

    if (errorMessage && errorMessage !== '') {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{errorMessage}</Text>
        </View>
      );
    }
  }

  onButtonPress(){
    // this.props.submitTriviaMission()
  }

  renderButton(){
    const { loading, isInCharge } = this.props

    return loading ? (
      this.renderSpinner()
    ) : (
        isInCharge ?
            (<Button
                mode='contained'
                style={styles.button}
                onPress={this.onButtonPress}>
              Submit Answer
            </Button>) : null
    )
  }


  render() {
    const { questions } = this.props;
    return (
      <View style={styles.container}>
        <KeyboardAwareScrollView>
          {this.renderTriviaQuestions(questions)}
          {this.renderButton()}
          {this.renderError()}
        </KeyboardAwareScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 0,
    width: "100%",
    alignSelf: "center",
  },
  button: {
    marginTop: 50,
    maxWidth: 280,
    alignSelf: "center",
  },
});

const mapStateToProps = (state) => {
  const {
    missionId,
    questions,
    apiKey,
    loading,
    errorMessage,
    reload,
    isInCharge,
  } = state.triviaMission;
  return {
    missionId,
    questions,
    apiKey,
    loading,
    errorMessage,
    reload,
    isInCharge,
  };
};

export default connect(mapStateToProps, {
  answerChanged,
})(TriviaMissionScreen);



