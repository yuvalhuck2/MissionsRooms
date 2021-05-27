import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import { RadioButton, ActivityIndicator, Appbar } from 'react-native-paper';
import ListAccordion from 'react-native-paper/src/components/List/ListAccordion';
import ListAccordionGroup from 'react-native-paper/src/components/List/ListAccordionGroup';
import RadioButtonGroup from 'react-native-paper/src/components/RadioButton/RadioButtonGroup';
import Button from '../common/Button';
import Header from '../common/Header';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';

const questions = [
  {
    id: '1',
    question: 'מי הוא ראש הממשלה?',
    answers: ['יאיר לפיד', 'בני גנץ', 'בנימין נתינהו', 'נפתלי בנט'],
    correctAnswer: 'בנימין נתינהו',
  },
  {
    id: '2',
    question: 'כמה זה 10:(2+2)?',
    answers: ['0.2', '0.4', '0', '2.2'],
    correctAnswer: '0.4',
  },
];

class SolveTriviaMission extends Component {
  constructor(...args) {
    super(...args);
    this.renderTriviaForm = this.renderTriviaForm.bind(this);
    this.renderQuestionAnswers = this.renderQuestionAnswers.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.onChatButtonPress = this.onChatButtonPress.bind(this);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
  }

  onAnswerChanged(text, index){
    this.props.answerChanged(text, index);
  }

  onChatButtonPress() {
    const { enterChatStudent, navigation } = this.props;
    enterChatStudent({ navigation });
  }

  onBackPress(){
    const { navigation, apiKey, roomId, mission, handleBack } = this.props;
    handleBack({ navigation, apiKey, roomId, mission });
  }

  onButtonPress() {
    const { roomId, mission, apiKey, navigation, currentAnswers, sendTriviaForm } = this.props;
    sendTriviaForm({
      roomId,
      mission,
      apiKey,
      navigation,
      currentAnswers,
    })
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

  renderButton(){
    const {loading, isInCharge} = this.props

    return loading ? (
      this.renderSpinner()
    ) : (
        isInCharge ?
            (<Button
                mode='contained'
                style={styles.button}
                onPress={this.onButtonPress}>
              {send_answer}
            </Button>) : null
    )
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



  renderQuestionAnswers({ answers, correctAnswer }) {
    let renderItem = ({ item }) => {
      return (
        <View
          style={{
            flex: 1,
            flexDirection: 'row',
            paddingLeft: 30,
            paddingRight: 30,
          }}
        >
          <Text style={{ flex: 1 }}>{item}</Text>
          <RadioButton style={{ flex: 1 }} value={item} />
        </View>
      );
    };

    let answerList = (
      <FlatList
        data={answers}
        renderItem={renderItem}
        keyExtractor={(item) => item}
      />
    );

    return (
      <RadioButtonGroup
        style={{ flex: 1, padding: 50 }}
        onValueChange={() => {}}
        value={correctAnswer}
      >
        {answerList}
      </RadioButtonGroup>
    );
  }

  renderTriviaForm() {
    let renderItem = ({ item }) => {
      return (
        <ListAccordion title={item.question} id={item.id}>
          {this.renderQuestionAnswers({
            answers: item.answers,
            correctAnswer: item.correctAnswer,
          })}
        </ListAccordion>
      );
    };
    return (
      <FlatList
        data={questions}
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
      />
    );
  }

  render() {
    return (
      <ListAccordionGroup style={styles.container}>
        <Appbar.Header>
          <Appbar.BackAction onPress={() => {this.onBackPress()}} />
          <Appbar.Action icon="chat" onPress={() => this.onChatButtonPress()} />
        </Appbar.Header>
        <Header>ביצוע משימת טריוויה</Header>
        {this.renderTriviaForm()}
        <View
          style={{ flex: 0.5, flexDirection: 'column', alignItems: 'center' }}
        >
          <Button mode='contained' style={styles.button}>
            שלח טופס
          </Button>
        </View>
      </ListAccordionGroup>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: '100%',
    alignItems: 'center',
  },
  button: {
    margin: 30,
    width: '80%',
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
  },
  chatButton:{
      //alignSelf: 'flex-end',
      position: 'absolute',
      top:550,
      right:0,
  },
});

export default SolveTriviaMission;
