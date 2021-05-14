import React, { Component } from 'react';
import { FlatList, KeyboardAvoidingView, StyleSheet } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { connect } from 'react-redux';
import { AddTriviaQuestionStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  header,
  addQuestion,
  addSubject,
  addWrongAnswer,
  addCorrectAnswer,
} = AddTriviaQuestionStrings;

const subjects = ['ספורט', 'אקטואליה', 'פוליטיקה'];

class AddTriviaQuestion extends Component {
  constructor(...args) {
    super(...args);
    this.renderDropDownSubject = this.renderDropDownSubject.bind(this);
    this.renderQuestionsInputs = this.renderQuestionsInputs.bind(this);
    this.renderQuestion = this.renderQuestion.bind(this);
    this.onChangeQuestion = this.onChangeQuestion.bind(this);
    this.onChangeWrongAnswer = this.onChangeWrongAnswer.bind(this);
    this.onChangeCorrectAnswer = this.onChangeCorrectAnswer.bind(this);
    this.onButtonPressed = this.onButtonPressed.bind(this);
    this.onChangeSubject = this.onChangeSubject.bind(this);
  }

  onChangeQuestion(text) {
    this.props.questionChanged(text);
  }

  onChangeWrongAnswer(text, key) {
    this.props.wrongAnswerChanged(text, key);
  }

  onChangeCorrectAnswer(text) {
    this.props.correctAnswerChanged(text);
  }

  onChangeSubject(text) {
    this.props.subjectChanged(text);
  }

  onButtonPressed() {
    const {
      apiKey,
      question,
      wrongAnswers,
      correctAnswer,
      questionSubject,
    } = this.props;
    this.props.addTriviaQuestion({
      apiKey,
      question,
      wrongAnswers,
      correctAnswer,
      subject: questionSubject,
    });
  }

  renderQuestion() {
    const { question } = this.props;
    return (
      <TextInput
        label={addQuestion}
        value={question}
        onChangeText={this.onChangeQuestion}
      />
    );
  }

  renderDropDownSubject() {
    return (
      <DropDownPicker
        items={subjects}
        containerStyle={{ height: 40 }}
        style={{ backgroundColor: '#fafafa' }}
        itemStyle={{
          justifyContent: 'flex-start',
        }}
        dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
        placeholder={addSubject}
        onChangeItem={this.onChangeSubject}
      />
    );
  }

  renderQuestionsInputs() {
    const { wrongAnswers, correctAnswer } = this.props;
    const inputParams = [
      { name: 'input', placeholder: { addWrongAnswer }, key: '0' },
      { name: 'input', placeholder: { addWrongAnswer }, key: '1' },
      { name: 'input', placeholder: { addWrongAnswer }, key: '2' },
      { name: 'input', placeholder: { addCorrectAnswer }, key: '3' },
      {
        name: 'button',
        placeholder: { addQuestion },
        key: '4',
      },
    ];

    let getInputValue = (item) => {
      let index = parseInt(item.key);
      return index === 3 ? correctAnswer : wrongAnswers[index];
    };

    let getOnChangeText = (text, key) => {
      let index = parseInt(key);
      return index === 3
        ? () => this.onChangeCorrectAnswer(text)
        : () => this.onChangeWrongAnswer(text, key);
    };

    let renderItem = ({ item }) => {
      item.name === 'input' ? (
        <TextInput
          label={item.placeholder}
          value={getInputValue()}
          onChangeText={getOnChangeText()}
        />
      ) : (
        <Button
          mode='contained'
          style={styles.button}
          onPress={this.onButtonPressed}
        >
          {item.placeholder}
        </Button>
      );
    };

    return (
      <FlatList
        data={inputParams}
        keyExtractor={(item) => item.key}
        renderItem={renderItem}
      />
    );
  }

  render() {
    return (
      <KeyboardAvoidingView style={styles.container}>
        <Header>{header}</Header>
        {this.renderQuestion()}
        {this.renderDropDownSubject()}
        {this.renderQuestionsInputs()}
      </KeyboardAvoidingView>
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

const mapStateToProps = (state) => {
  const {
    apiKey,
    loading,
    errorMessage,
    question,
    wrongAnswers,
    correctAnswer,
    questionSubject,
    subjects,
  } = state.AddTriviaQuestion;

  return {
    apiKey,
    loading,
    errorMessage,
    question,
    wrongAnswers,
    correctAnswer,
    questionSubject,
    subjects,
  };
};

export default connect(mapStateToProps, {
  questionChanged,
  wrongAnswerChanged,
  correctAnswerChanged,
  addTriviaQuestion,
  subjectChanged,
})(AddTriviaQuestion);
