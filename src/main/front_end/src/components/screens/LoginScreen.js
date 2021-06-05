import React, { Component } from "react";
import {
  SafeAreaView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  Image,
} from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { ActivityIndicator } from "react-native-paper";
import { connect } from "react-redux";
import {
  emailChanged,
  loginUser,
  navigateToRegister,
  navigateToResetPassword,
  passwordChanged,
} from "../../actions";
import { theme } from "../../core/theme";
import { loginStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
  header,
  enter_email,
  enter_password,
  login_btn,
  no_user,
  no_user_sign_up,
  forgat_password,
  press_here,
} = loginStrings;

class LoginForm extends Component {
  constructor(...args) {
    super(...args);
    this.onEmailChange = this.onEmailChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.navigateToRegister = this.navigateToRegister.bind(this);
    this.navigateToResetPassword = this.navigateToResetPassword.bind(this);
  }

  onEmailChange(text) {
    this.props.emailChanged(text);
  }

  onPasswordChange(text) {
    this.props.passwordChanged(text);
  }

  onButtonPress() {
    const { email, password, navigation } = this.props;
    this.props.loginUser({ email, password, navigation });
  }

  navigateToRegister() {
    const { navigation } = this.props;
    this.props.navigateToRegister({ navigation });
  }

  navigateToResetPassword() {
    const { navigation } = this.props;
    this.props.navigateToResetPassword({ navigation });
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
        {login_btn}
      </Button>
    );
  }

  renderRegisterLabel() {
    const { loading } = this.props;

    return loading ? null : (
      <View style={styles.row}>
        <Text style={styles.label}>{no_user}</Text>
        <TouchableOpacity onPress={this.navigateToRegister}>
          <Text style={styles.link}>{no_user_sign_up}</Text>
        </TouchableOpacity>
      </View>
    );
  }

  renderResetPasswordLabel() {
    const { loading } = this.props;

    return loading ? null : (
      <View style={styles.row}>
        <Text style={styles.label}>{forgat_password}</Text>
        <TouchableOpacity onPress={this.navigateToResetPassword}>
          <Text style={styles.link}>{press_here}</Text>
        </TouchableOpacity>
      </View>
    );
  }

  render() {
    const { email, password } = this.props;

    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAwareScrollView>
          <CustomAppbar backAction={this.props.navigation.goBack} />
          <Header>{header}</Header>
          <TextInput
            label={enter_email}
            value={email}
            onChangeText={this.onEmailChange}
            placeholder="roy4@post.bgu.ac.il"
          />

          <TextInput
            label={enter_password}
            value={password}
            onChangeText={this.onPasswordChange}
            placeholder="Th1sI5@passWord"
            secureTextEntry
          />

          {this.renderButton()}
          {this.renderError()}
          {this.renderRegisterLabel()}
          {this.renderResetPasswordLabel()}

          <View style={{ alignItems: "center", justifyContent: "center" }}>
            <Image
              style={{ marginTop: 20, width: 180, height: 200 }}
              source={require("../../../assets/full_logo.png")}
            />
          </View>
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
  const { email, password, loading, errorMessage } = state.auth;
  return { email, password, loading, errorMessage };
};

export default connect(mapStateToProps, {
  emailChanged,
  passwordChanged,
  loginUser,
  navigateToRegister,
  navigateToResetPassword,
})(LoginForm);
