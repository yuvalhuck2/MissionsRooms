import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { ActivityIndicator, Appbar } from 'react-native-paper';
import { connect } from 'react-redux';
import {
  addSubject,
  handleBack,
  subjectChanged,
} from '../../actions/AddTriviaSubjectActions';
import { theme } from '../../core/theme';
import { AddTriviaSubjectStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  header,
  inputPlaceholder,
  addButtonText,
  labelPlaceholder,
} = AddTriviaSubjectStrings;

class AddTriviaSubject extends Component {
  constructor(...args) {
    super(...args);
    this.onBackPress = this.onBackPress.bind(this);
    this.onSubjectChange = this.onSubjectChange.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.renderButton = this.renderButton.bind(this);
    this.renderError = this.renderError.bind(this);
  }

  onBackPress() {
    const { navigation, handleBack } = this.props;
    handleBack({ navigation });
  }

  onSubjectChange(text) {
    this.props.subjectChanged(text);
  }

  onButtonPress() {
    const { apiKey, subject } = this.props;
    this.props.addSubject({ apiKey, subject });
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
        {addButtonText}
      </Button>
    );
  }

  renderError() {
    const { errorMessage, loading } = this.props;

    if (!loading && errorMessage && errorMessage !== '') {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{errorMessage}</Text>
        </View>
      );
    }
  }

  render() {
    const { subject } = this.props;
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

        <TextInput
          label={labelPlaceholder}
          placeholder={inputPlaceholder}
          value={subject}
          onChangeText={this.onSubjectChange}
        />
        {this.renderButton()}
        {this.renderError()}
      </View>
    );
  }
}

const mapStateToProps = (state) => {
  const { apiKey, loading, errorMessage, subject } = state.addTriviaSubject;
  return { apiKey, loading, errorMessage, subject };
};

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

export default connect(mapStateToProps, {
  subjectChanged,
  addSubject,
  handleBack,
})(AddTriviaSubject);
