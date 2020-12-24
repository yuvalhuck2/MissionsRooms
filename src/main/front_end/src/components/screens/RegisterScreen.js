import React, { Component } from 'react';
import { Text, View } from 'react-native';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged } from '../../actions';

class RegisterForm extends Component {
  constructor(...args) {
    super(...args);
    this.onEmailChange = this.onEmailChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
  }

  onEmailChange(text) {
    this.props.emailChanged(text);
  }

  onPasswordChange(text) {
    this.props.passwordChanged(text);
  }

  render() {
    return (
      <View>
        <Text>Register Screen</Text>
      </View>
    );
  }
}

const mapStateToProps = (state) => {
  console.log(state);
  const { email, password } = state.auth;
  return { email, password };
};

export default connect(mapStateToProps, {
  emailChanged,
  passwordChanged,
})(RegisterForm);
