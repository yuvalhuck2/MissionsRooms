import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  addIT,
  aliasChanged,
  navigateToITScreen,
  passwordChanged,
} from "../../actions/AddITActions";
import { theme } from "../../core/theme";
import { addITStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const { header, enter_alias, enter_password, add_IT_btn } = addITStrings;

class AddITFrom extends Component {
  constructor(...args) {
    super(...args);
    this.onAliasChange = this.onAliasChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.OnGoBack = this.OnGoBack.bind(this);
  }

  onAliasChange(text) {
    this.props.aliasChanged(text);
  }

  onPasswordChange(text) {
    this.props.passwordChanged(text);
  }

  onButtonPress() {
    const { alias, password, apiKey } = this.props;
    this.props.addIT({ alias, password, apiKey });
  }

  OnGoBack() {
    const { navigation, apiKey } = this.props;
    this.props.navigateToITScreen({ navigation, apiKey });
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
        {add_IT_btn}
      </Button>
    );
  }

  render() {
    const { alias, password } = this.props;

    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAwareScrollView>
          <CustomAppbar backAction={this.OnGoBack} />

          <Header>{header}</Header>
          <TextInput
            label={enter_alias}
            value={alias}
            onChangeText={this.onAliasChange}
          />

          <TextInput
            label={enter_password}
            value={password}
            onChangeText={this.onPasswordChange}
            secureTextEntry
          />

          {this.renderButton()}
          {this.renderError()}
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
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const { alias, password, loading, errorMessage, apiKey } = state.addIT;
  return { alias, password, loading, errorMessage, apiKey };
};

export default connect(mapStateToProps, {
  aliasChanged,
  passwordChanged,
  addIT,
  navigateToITScreen,
})(AddITFrom);
