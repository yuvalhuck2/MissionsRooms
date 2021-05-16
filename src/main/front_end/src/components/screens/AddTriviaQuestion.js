import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { ActivityIndicator, Appbar } from 'react-native-paper';
import { connect } from 'react-redux';
import {
  addTriviaQuestion,
  correctAnswerChanged,
  getCurrentSubjects,
  questionChanged,
  subjectChanged,
  wrongAnswerChanged,
} from '../../actions/AddTriviaQuestionActions';
import { theme } from '../../core/theme';
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
    this.renderError = this.renderError.bind(this);
    this.renderSpinner = this.renderSpinner.bind(this);
    this.renderSubmitButton = this.renderSubmitButton.bind(this);
    this.onBackPress = this.onBackPress(this);
  }

  componentDidMount() {
    this.props.getCurrentSubjects({ apiKey: this.props.apiKey });
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
    this.props.subjectChanged(text.value);
  }

  onBackPress() {
    const { navigation, handleBack, apiKey } = this.props;
    handleBack({ navigation, apiKey });
  }

  onButtonPressed() {
    const {
      apiKey,
      question,
      wrongAnswers,
      correctAnswer,
      questionSubject,
      navigation,
    } = this.props;
    this.props.addTriviaQuestion({
      apiKey,
      question,
      wrongAnswers,
      correctAnswer,
      subject: questionSubject,
      navigation,
    });
  }

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

  renderSubmitButton(placeholder) {
    const { loading } = this.props;

    return loading ? (
      this.renderSpinner()
    ) : (
      <>
        <Button
          mode='contained'
          style={styles.button}
          onPress={this.onButtonPressed}
        >
          {placeholder}
        </Button>
        {this.renderError()}
      </>
    );
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
    const { subjects } = this.props;
    let mappedSubjects = subjects.map((sub) => {
      return { label: sub, value: sub };
    });
    return (
      <DropDownPicker
        items={mappedSubjects}
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
      { name: 'input', placeholder: addWrongAnswer, key: '0' },
      { name: 'input', placeholder: addWrongAnswer, key: '1' },
      { name: 'input', placeholder: addWrongAnswer, key: '2' },
      { name: 'input', placeholder: addCorrectAnswer, key: '3' },
      {
        name: 'button',
        placeholder: addQuestion,
        key: '4',
      },
    ];

    let inputValues = [...wrongAnswers, correctAnswer];

    let renderItem = ({ item }) => {
      return item.name === 'input' ? (
        <TextInput
          label={item.placeholder}
          value={inputValues[parseInt(item.key)]}
          onChangeText={(text) => {
            let index = parseInt(item.key);
            if (index === 3) {
              this.onChangeCorrectAnswer(text);
            } else {
              this.onChangeWrongAnswer(text, item.key);
            }
          }}
        />
      ) : (
        this.renderSubmitButton(item.placeholder)
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
      <View style={styles.container}>
        <Header>{header}</Header>
        <Appbar.Header styles={styles.bottom}>
          <Appbar.BackAction
            onPress={() => {
              this.onBackPress();
            }}
          />
        </Appbar.Header>
        {this.renderQuestion()}
        {this.renderDropDownSubject()}
        {this.renderQuestionsInputs()}
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
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
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
  } = state.addTriviaQuestion;

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
  getCurrentSubjects,
})(AddTriviaQuestion);
