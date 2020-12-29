import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged, registerUser } from '../../actions';
import { theme } from '../../core/theme';
import { registerStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  header,
  enter_email,
  enter_password,
  register_btn,
  already_user,
  already_user_sign_in,
} = registerStrings;

class RegisterForm extends Component {
  constructor(...args) {
    super(...args);
    this.onEmailChange = this.onEmailChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
  }

  onEmailChange(text) {
    this.props.emailChanged(text);
  }

  onPasswordChange(text) {
    this.props.passwordChanged(text);
  }

  onButtonPress() {
    const { email, password } = this.props;
    this.props.registerUser({ email, password });
  }

  render() {
    const { email, password } = this.props;

    return (
      <KeyboardAwareScrollView style={styles.container}>
        <Header>{header}</Header>
        <TextInput
          label={enter_email}
          value={email}
          onChangeText={this.onEmailChange}
          placeholder='roy4@post.bgu.ac.il'
        />

        <TextInput
          label={enter_password}
          value={password}
          onChangeText={this.onPasswordChange}
          placeholder='Th1sI5@passWord'
          secureTextEntry
        />

        <Button
          mode='contained'
          style={styles.button}
          onPress={this.onButtonPress}
        >
          {register_btn}
        </Button>

        <View style={styles.row}>
          <Text style={styles.label}>{already_user}</Text>
          <TouchableOpacity
            onPress={() => this.props.navigation.navigate('Login')}
          >
            <Text style={styles.link}>{already_user_sign_in}</Text>
          </TouchableOpacity>
        </View>
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
});

const mapStateToProps = (state) => {
  const { email, password } = state.auth;
  return { email, password };
};

export default connect(mapStateToProps, {
  emailChanged,
  passwordChanged,
  registerUser,
})(RegisterForm);
