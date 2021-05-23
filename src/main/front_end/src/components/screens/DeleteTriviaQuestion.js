import React, { Component } from "react";
import { FlatList, StyleSheet, Text, View } from "react-native";
import { RadioButton } from "react-native-paper";
import ListAccordion from "react-native-paper/src/components/List/ListAccordion";
import ListAccordionGroup from "react-native-paper/src/components/List/ListAccordionGroup";
import RadioButtonGroup from "react-native-paper/src/components/RadioButton/RadioButtonGroup";
import Button from "../common/Button";
import Header from "../common/Header";

const questions = [
  {
    id: "1",
    question: "מי הוא ראש הממשלה?",
    answers: ["יאיר לפיד", "בני גנץ", "בנימין נתינהו", "נפתלי בנט"],
    correctAnswer: "בנימין נתינהו",
  },
  {
    id: "2",
    question: "כמה זה 10:(2+2)?",
    answers: ["0.2", "0.4", "0", "2.2"],
    correctAnswer: "0.4",
  },
];

class DeleteTriviaQuestion extends Component {
  constructor(...args) {
    super(...args);
    this.renderTriviaForm = this.renderTriviaForm.bind(this);
    this.renderQuestionAnswers = this.renderQuestionAnswers.bind(this);
  }

  renderQuestionAnswers({ answers, correctAnswer }) {
    let renderItem = ({ item }) => {
      return (
        <View
          style={{
            flex: 1,
            flexDirection: "row",
            paddingLeft: 30,
            paddingRight: 30,
            alignItems: 'center'
          }}
        >
          <Text>{item}</Text>
          <RadioButton value={item} />
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
        // style={{ flex: 1, padding: 50 }}
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
        <View
          style={
            {
              // flex: 1,
              // flexDirection: 'row',
              // alignItems: 'stretch',
              // paddingLeft: 200,
              // paddingRight: 30,
            }
          }
        >
          {/* <RadioButton value={questions[0]} style={{ flex: 1 }} /> */}

          <ListAccordion
            title={item.question}
            id={item.id}
            // style={{ flex: 30 }}
          >
            {this.renderQuestionAnswers({
              answers: item.answers,
              correctAnswer: item.correctAnswer,
            })}
          </ListAccordion>
        </View>
      );
    };
    return (
      <RadioButtonGroup>
        <FlatList
          data={questions}
          renderItem={renderItem}
          keyExtractor={(item) => item.id}
        />
      </RadioButtonGroup>
    );
  }

  render() {
    return (
      <ListAccordionGroup style={styles.container}>
        <Header>מחק שאלת טריוויה</Header>
        {this.renderTriviaForm()}
        {/* <View
          style={{ flex: 0.5, flexDirection: 'column', alignItems: 'center' }}
        > */}
        <Button mode="contained" style={styles.button}>
          מחק שאלה
        </Button>
      </ListAccordionGroup>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    // width: '100%',
    alignItems: 'center',
    width: "100%",
    maxWidth: 340,
    // alignSelf: "center",
    justifyContent: "center",
  },
  button: {
    margin: 30,
    width: "80%",
    // height: "80%",
  },
});

export default DeleteTriviaQuestion;
