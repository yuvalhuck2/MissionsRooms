import React, { Component } from 'react';
import { FlatList, KeyboardAvoidingView, StyleSheet } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
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
  }

  renderQuestion() {
    return <TextInput label={addQuestion} />;
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
      />
    );
  }

  renderQuestionsInputs() {
    const inputParams = [
      { name: 'input', placeholder: { addWrongAnswer }, key: '1' },
      { name: 'input', placeholder: { addWrongAnswer }, key: '2' },
      { name: 'input', placeholder: { addWrongAnswer }, key: '3' },
      { name: 'input', placeholder: { addCorrectAnswer }, key: '4' },
      {
        name: 'button',
        placeholder: { addQuestion },
        key: '5',
      },
    ];

    return (
      <FlatList
        data={inputParams}
        keyExtractor={(item) => item.key}
        renderItem={({ item }) =>
          item.name === 'input' ? (
            <TextInput label={item.placeholder} />
          ) : (
            <Button mode='contained' style={styles.button}>
              {item.placeholder}
            </Button>
          )
        }
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

export default AddTriviaQuestion;
