import React, { Component } from "react";
import { FlatList, SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator, RadioButton } from "react-native-paper";
import { connect } from "react-redux";
import { getTriviaQuestions } from "../../actions/AddMissionActions";
import {
  deleteTriviaQuestion,
  handleBack,
  questionChanged,
} from "../../actions/DeleteTriviaQuestionActions";
import { theme } from "../../core/theme";
import { deleteTriviaQuestionStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";

const { header, button } = deleteTriviaQuestionStrings;

class DeleteTriviaQuestion extends Component {
  constructor(...args) {
    super(...args);
    this.renderTriviaForm = this.renderTriviaForm.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.questionChanged = this.questionChanged.bind(this);
    this.renderButton = this.renderButton.bind(this);
    this.renderError = this.renderError.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
  }

  componentDidMount() {
    this.props.getTriviaQuestions({ apiKey: this.props.apiKey });
  }

  onBackPress() {
    const { navigation, handleBack } = this.props;
    handleBack({ navigation });
  }

  onButtonPress() {
    const { apiKey, navigation, selectedQuestion } = this.props;
    this.props.deleteTriviaQuestion({
      apiKey,
      navigation,
      questionStr: selectedQuestion,
    });
  }

  questionChanged(newQuestion) {
    this.props.questionChanged(newQuestion);
  }

  renderTriviaForm() {
    let renderItem = ({ item }) => {
      return (
        <View
          style={{
            flex: 1,
            flexDirection: "row",
            justifyContent: "flex-start",
            alignItems: "flex-start",
          }}
        >
          {/* <Text>{item.question}</Text> */}
          <RadioButton.Item label={item.question} value={item} />
        </View>
      );
    };

    return (
      <View style={{ flex: 0.5 }}>
        <RadioButton.Group
          onValueChange={(newValue) => this.questionChanged(newValue)}
          value={this.props.selectedQuestion}
        >
          <FlatList
            data={this.props.questions}
            renderItem={renderItem}
            keyExtractor={(item) => item.id}
          />
        </RadioButton.Group>
      </View>
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
        {button}
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

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackPress} />
        <View style={{ flex: 1, alignItems: "center" }}>
          <Header>{header}</Header>
          {this.renderTriviaForm()}
          {this.renderButton()}
          {this.renderError()}
        </View>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    // justifyContent: 'center',
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
  },
  button: {
    margin: 30,
  },
  bottom: {
    margin: 30,
  },
  errorTextStyle: {
    fontSize: 25,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

export const mapStateToProps = (state) => {
  const {
    apiKey,
    questions,
    selectedQuestion,
    errorMessage,
    loading,
  } = state.deleteTriviaQuestion;
  return { apiKey, questions, selectedQuestion, errorMessage, loading };
};

export default connect(mapStateToProps, {
  questionChanged,
  deleteTriviaQuestion,
  getTriviaQuestions,
  handleBack,
})(DeleteTriviaQuestion);
