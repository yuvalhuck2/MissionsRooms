import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator } from 'react-native-paper';
import { connect } from 'react-redux';
import { answerChanged, sendDeterministicAnswer } from '../../actions/ChooseStudentRoomActions';
import { theme } from '../../core/theme';
import { SolveDeterministicMissionStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  enter_answer,
  send_answer,
} = SolveDeterministicMissionStrings;

class SolveDeterministicForm extends Component {
  constructor(...args) {
    super(...args);
    this.onAnswerChanged = this.onAnswerChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
  }

  onAnswerChanged(text) {
    const {currentMission } = this.props;
    this.props.answerChanged(currentMission,text);
  }

  onButtonPress() {
    const {currentRoom,currentMission,apiKey, navigation } = this.props;
    this.props.sendDeterministicAnswer({currentRoom,currentMission,apiKey, navigation });
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
    const {currentMission} = this.props

    return currentMission.loading ? (
      this.renderSpinner()
    ) : (
      <Button
      mode='contained'
      style={styles.button}
      onPress={this.onButtonPress}
      >
      {send_answer}
      </Button>
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

  render() {
    const { currentMission } = this.props;

    return (
      <KeyboardAwareScrollView style={styles.container}>
        <Header>{currentMission.question}</Header>
        <TextInput
          label={enter_answer}
          value={currentMission.currentAnswer}
          onChangeText={this.onAnswerChanged}
          placeholder='תשובה'
        />
        {this.renderButton()}
        {this.renderError()}
      </KeyboardAwareScrollView>
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 24,
  },
  row: {
    flexDirection: 'row',
    marginTop: 10,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const { currentRoom,currentMission,apiKey, errorMessage } = state.ChooseStudentRoom;
  return { currentRoom,currentMission,apiKey, errorMessage };
};

export default connect(mapStateToProps, {
  answerChanged,
  sendDeterministicAnswer,
})(SolveDeterministicForm);
