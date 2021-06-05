import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { connect } from "react-redux";
import { addSuggestion, suggestionChange } from "../../actions";
import { theme } from "../../core/theme";
import { AddSuggestionStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const { header, addSuggestionText, send_suggestion } = AddSuggestionStrings;

class AddSuggestionForm extends Component {
  constructor(...args) {
    super(...args);
    this.onSuggestionChange = this.onSuggestionChange.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  onSuggestionChange(text) {
    this.props.suggestionChange(text);
  }

  onButtonPress() {
    const { apiKey, suggestion, navigation } = this.props;
    this.props.addSuggestion({ apiKey, suggestion, navigation });
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
        {send_suggestion}
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

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    const { suggestion } = this.props;
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackAction} />
        <KeyboardAwareScrollView style={styles.container}>
          <Header>{header}</Header>
          <TextInput
            label={addSuggestionText}
            value={suggestion}
            onChangeText={this.onSuggestionChange}
          />
          {this.renderButton()}
        </KeyboardAwareScrollView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 24,
  },
  row: {
    flexDirection: "row",
    marginTop: 10,
  },
  link: {
    fontWeight: "bold",
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 25,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const { suggestion, apiKey, loading } = state.addSuggestion;
  return { suggestion, apiKey, loading };
};

export default connect(mapStateToProps, {
  suggestionChange,
  addSuggestion,
})(AddSuggestionForm);
