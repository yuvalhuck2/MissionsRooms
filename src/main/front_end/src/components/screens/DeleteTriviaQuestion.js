import React, { Component } from 'react';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import { ActivityIndicator, RadioButton, Appbar } from 'react-native-paper';
import RadioButtonGroup from 'react-native-paper/src/components/RadioButton/RadioButtonGroup';
import { connect } from 'react-redux';
import { getTriviaQuestions } from '../../actions/AddMissionActions';
import {
  deleteTriviaQuestion,
  questionChanged,
  handleBack,
} from '../../actions/DeleteTriviaQuestionActions';
import { theme } from '../../core/theme';
import { deleteTriviaQuestionStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';

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
        <View style={{ flex: 1, flexDirection: 'row' }}>
          <Text>{item.question}</Text>
          <RadioButton value={item} />
        </View>
      );
    };

    return (
      <View style={{flex: 0.5}}>
        <RadioButtonGroup
          onValueChange={(newValue) => this.questionChanged(newValue)}
          value={this.props.selectedQuestion}
        >
          <FlatList
            data={this.props.questions}
            renderItem={renderItem}
            keyExtractor={(item) => item.id}
          />
        </RadioButtonGroup>
      </View>
    );
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

  renderButton() {
    const { loading } = this.props;
    return loading ? (
      this.renderSpinner()
    ) : (
      <Button
        mode='contained'
        style={styles.button}
        onPress={this.onButtonPress}
      >
        {button}
      </Button>
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

  render() {
    return (
      <View style={styles.container}>
        <Appbar.Header styles={styles.bottom}>
          <Appbar.BackAction
            onPress={() => {
              this.onBackPress();
            }}
          />
        </Appbar.Header>
        <Header>{header}</Header>
        {this.renderTriviaForm()}
        {this.renderButton()}
        {this.renderError()}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    alignItems: 'center',
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
  },
  button: {
    margin: 30,
  },
  errorTextStyle: {
    fontSize: 25,
    alignSelf: 'center',
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
