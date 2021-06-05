import React, { Component } from "react";
import { FlatList, SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator, RadioButton } from "react-native-paper";
import ListAccordion from "react-native-paper/src/components/List/ListAccordion";
import ListAccordionGroup from "react-native-paper/src/components/List/ListAccordionGroup";
import { connect } from "react-redux";
import {
  enterChatStudent,
  handleBack,
} from "../../actions/ChooseStudentRoomActions";
import { sendTriviaForm } from "../../actions/solveTriviaMissionActions";
import { theme } from "../../core/theme";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";

class SolveTriviaMission extends Component {
  constructor(...args) {
    super(...args);
    this.renderTriviaForm = this.renderTriviaForm.bind(this);
    this.renderQuestionAnswers = this.renderQuestionAnswers.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.onChatButtonPress = this.onChatButtonPress.bind(this);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.initState = this.initState.bind(this);
    this.state = {
      currentAnswers: [],
      questions: [],
      answers: [],
    };
  }

  componentDidMount() {
    this.initState();
  }

  componentDidUpdate(prevProps, prevState) {
    if (prevProps.mission !== this.props.mission) {
      this.initState();
    }
  }

  initState() {
    const { mission } = this.props;
    const triviaQuestions = Object.values(mission.triviaQuestionMap);
    const triviaAnswers = triviaQuestions.map((question, questNum) => {
      let currAnswers = [...question.answers, question.correctAnswer];
      currAnswers = currAnswers.sort((a, b) => 0.5 - Math.random());
      let answersWithIndex = [];
      for (let i = 0; i < currAnswers.length; i++) {
        answersWithIndex.push({
          answer: currAnswers[i],
          index: i,
          questNum,
        });
      }
      return answersWithIndex;
    });
    const currentAnswers = triviaAnswers.map((answers) => answers[0]);

    this.setState({
      questions: triviaQuestions,
      answers: triviaAnswers,
      currentAnswers,
    });
  }

  onAnswerChanged(value) {
    let newCurrentAnswers = [...this.state.currentAnswers];
    newCurrentAnswers[value.questNum] = value;
    this.setState({ currentAnswers: newCurrentAnswers });
  }

  onChatButtonPress() {
    const { enterChatStudent, navigation } = this.props;
    enterChatStudent({ navigation });
  }

  onBackPress() {
    const { navigation, apiKey, roomId, mission, handleBack } = this.props;
    handleBack({ navigation, apiKey, roomId, mission });
  }

  onButtonPress() {
    const { roomId, mission, apiKey, navigation, sendTriviaForm } = this.props;

    const { currentAnswers } = this.state;
    let actualAnswers = currentAnswers.map((value) => value.answer);
    sendTriviaForm({
      roomId,
      mission,
      apiKey,
      navigation,
      currentAnswers: actualAnswers,
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
        שלח טופס
      </Button>
    ) : null;
  }

  // renderButton(){
  //   const { loading, isInCharge } = this.props;

  //   return loading ? (
  //     this.renderSpinner()
  //   ) : (
  //     <Button
  //       mode="contained"
  //       style={styles.button}
  //       onPress={this.onButtonPress}
  //     >
  //       שלח טופס
  //     </Button>
  //   )
  // }

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

  renderQuestionAnswers({ answers, currentAnswer }) {
    let renderItem = ({ item }) => {
      return (
        <View
          style={{
            flex: 1,
            flexDirection: "row",
            paddingLeft: 30,
            paddingRight: 30,
          }}
        >
          {/* <Text style={{ flex: 1 }}>{item}</Text> */}
          <RadioButton.Item
            style={{ flex: 1 }}
            label={item.answer}
            value={item}
          />
        </View>
      );
    };

    let answerList = (
      <FlatList
        data={answers}
        renderItem={renderItem}
        keyExtractor={(item) => item.index}
      />
    );
    return (
      <RadioButton.Group
        style={{ flex: 1, padding: 50 }}
        onValueChange={(newValue) => this.onAnswerChanged(newValue)}
        value={currentAnswer}
      >
        {answerList}
      </RadioButton.Group>
    );
  }

  renderTriviaForm() {
    let renderItem = ({ item, index }) => {
      return (
        <ListAccordion title={item.question} id={item.id}>
          {this.renderQuestionAnswers({
            answers: this.state.answers[index],
            currentAnswer: this.state.currentAnswers[index],
          })}
        </ListAccordion>
      );
    };
    return (
      <FlatList
        data={this.state.questions}
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
      />
    );
  }

  render() {
    return (
      <SafeAreaView style={styles.containe}>
        <ListAccordionGroup>
          <CustomAppbar
            backAction={this.onBackPress}
            actions={[{ icon: "chat", onPress: this.onChatButtonPress }]}
          />
          <Header>ביצוע משימת טריוויה</Header>
          {this.renderTriviaForm()}
          <View
            style={{ flex: 0.5, flexDirection: "column", alignItems: "center" }}
          >
            {this.renderButton()}
            {this.renderError()}
          </View>
        </ListAccordionGroup>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%",
    alignItems: "center",
  },
  button: {
    margin: 30,
    width: "80%",
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
  chatButton: {
    position: "absolute",
    top: 550,
    right: 0,
  },
});

const mapStateToProps = (state) => {
  const {
    roomId,
    mission,
    apiKey,
    navigation,
    isInCharge,
  } = state.solveTriviaMission;
  console.log(mission);
  return { roomId, mission, apiKey, navigation, isInCharge };
};

export default connect(mapStateToProps, {
  handleBack,
  sendTriviaForm,
  enterChatStudent,
})(SolveTriviaMission);
