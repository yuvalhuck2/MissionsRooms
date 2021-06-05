import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  addSubject,
  handleBack,
  subjectChanged,
} from "../../actions/AddTriviaSubjectActions";
import { theme } from "../../core/theme";
import { AddTriviaSubjectStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

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
        {addButtonText}
      </Button>
    );
  }

  renderError() {
    const { errorMessage, loading } = this.props;

    if (!loading && errorMessage && errorMessage !== "") {
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
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackPress} />
        <Header>{header}</Header>

        <TextInput
          label={labelPlaceholder}
          placeholder={inputPlaceholder}
          value={subject}
          onChangeText={this.onSubjectChange}
        />
        {this.renderButton()}
        {this.renderError()}
      </SafeAreaView>
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
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
  },
  button: {
    marginTop: 24,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

export default connect(mapStateToProps, {
  subjectChanged,
  addSubject,
  handleBack,
})(AddTriviaSubject);
