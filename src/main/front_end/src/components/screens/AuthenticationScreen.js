import CodeInput from '@andreferi/react-native-confirmation-code-input';
import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, SafeAreaView } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator } from 'react-native-paper';
import { connect } from 'react-redux';
import {
  codeChanged,
  registerCode,
  registerUser
} from '../../actions/AuthActions';
import { theme } from '../../core/theme';
import { authStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import CustomAppbar from '../common/CustomAppbar';
import Header from '../common/Header';

const { header, enter_code, choose_group, send_btn, no_code } = authStrings;

const data = [
  { label: 'Banana', value: 'Banana' },
  { label: 'Mango', value: 'Mango' },
  { label: 'Pear', value: 'Pear' },
];

class AuthForm extends Component {
  constructor(...args) {
    super(...args);
    this.onCodeChanged = this.onCodeChanged.bind(this);
    this.onButtonPressed = this.onButtonPressed.bind(this);
    this.onSendAgainButtonPress = this.onSendAgainButtonPress.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
    this.state = {
      groupsData: [],
      selectedGroupData: null,
    };
  }

  componentDidUpdate(prevProps) {
    if (prevProps.teachersData !== this.props.teachersData) {
      const { teachersData } = this.props;
      var data = [];
      if (teachersData && teachersData.length != 0) {
        teachersData.forEach((tData) => {
          if (tData.groupType === 'BOTH') {
            data = [
              ...data,
              {
                label: `${tData.firstName} ${tData.lastName} א`,
                value: {
                  teacherAlias: tData.alias,
                  groupType: 'A',
                },
              },
              {
                label: `${tData.firstName} ${tData.lastName} ב`,
                value: {
                  teacherAlias: tData.alias,
                  groupType: 'B',
                },
              },
            ];
          } else {
            data = [
              ...data,
              {
                label: `${tData.firstName} ${tData.lastName}`,
                value: {
                  teacherAlias: tData.alias,
                  groupType: tData.groupType,
                },
              },
            ];
          }
        });

        this.setState({ groupsData: data });
      }
    }
  }

  onButtonPressed() {
    const { navigation, userType } = this.props;

    if (userType === 'Teacher') {
      const { email, authCode } = this.props;
      this.props.registerCode({
        alias: email,
        code: authCode,
        teacherAlias: email,
        groupType: 'C',
        navigation,
      });
    } else {
      if (this.state.selectedGroupData) {
        const { email, authCode } = this.props;
        const { teacherAlias, groupType } = this.state.selectedGroupData;
        this.props.registerCode({
          alias: email,
          code: authCode,
          teacherAlias,
          groupType,
          navigation,
        });
      }
    }
  }

  onSendAgainButtonPress() {
    const { email, password } = this.props;
    this.props.registerUser({ email, password });
  }

  onCodeChanged(code) {
    this.props.codeChanged(code);
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
        onPress={this.onButtonPressed}
      >
        {send_btn}
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

  renderSendAgainLabel() {
    const { loading } = this.props;

    return loading ? null : (
      <View style={styles.row}>
        <Text style={styles.label}>{no_code}</Text>
        <TouchableOpacity
          onPress={this.onSendAgainButtonPress}
        ></TouchableOpacity>
      </View>
    );
  }

  renderDropDownPicker() {
    const { userType } = this.props;

    if (userType === 'Teacher') {
      return null;
    } else {
      return (
        <DropDownPicker
          items={this.state.groupsData}
          containerStyle={{ height: 40 }}
          style={{ backgroundColor: '#fafafa' }}
          itemStyle={{
            justifyContent: 'flex-start',
          }}
          dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
          placeholder={choose_group}
          onChangeItem={(item) =>
            this.setState({ selectedGroupData: item.value })
          }
        />
      );
    }
  }

  onBackAction(){
    const {navigation} = this.props;
    navigation.goBack();
  }

  render() {
    return (
      <SafeAreaView style={styles.container}>
      <KeyboardAwareScrollView>
        <CustomAppbar backAction={this.onBackAction}/>
        <Header>{header}</Header>

        <CodeInput
          keyboardType='numeric'
          codeLength={5}
          activeColor='rgba(148,0,211,1)'
          inactiveColor='rgba(148,0,211,1.3)'
          className='border-l-r'
          autoFocus={false}
          codeInputStyle={{ fontWeight: '800' }}
          containerStyle={{ marginTop: 30, marginBottom: 30 }}
          codeInputStyle={{ borderWidth: 1.5 }}
          cellBorderWidth={1.5}
          onFulfill={this.onCodeChanged}
        />
        {this.renderDropDownPicker()}
        {this.renderButton()}
        {this.renderError()}
        {this.renderSendAgainLabel()}
      </KeyboardAwareScrollView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 0,
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
  },
  button: {
    marginTop: 140,
  },
  row: {
    flexDirection: 'row',
    marginTop: 15,
    alignSelf: 'center',
  },
  label: {
    fontWeight: 'bold',
    color: '#9400D3',
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const {
    teachersData,
    authCode,
    loading,
    errorMessage,
    email,
    password,
    userType,
  } = state.auth;
  return {
    teachersData,
    authCode,
    loading,
    errorMessage,
    email,
    password,
    userType,
  };
};

export default connect(mapStateToProps, {
  codeChanged,
  registerCode,
  registerUser,
})(AuthForm);
