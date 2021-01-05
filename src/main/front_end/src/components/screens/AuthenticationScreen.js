import CodeInput from '@andreferi/react-native-confirmation-code-input';
import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { authStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
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
  }

  render() {
    const { code, group } = this.props;

    return (
      <KeyboardAwareScrollView style={styles.container}>
        <Header>{header}</Header>

        <CodeInput
          ref='codeInputRef2'
          keyboardType='numeric'
          codeLength={5}
          activeColor='rgba(148,0,211,1)'
          inactiveColor='rgba(148,0,211,1.3)'
          className='border-l-r'
          autoFocus={false}
          codeInputStyle={{ fontWeight: '800' }}
          containerStyle={{ marginTop: 30, marginBottom: 30 }}
          codeInputStyle={{ borderWidth: 1.5 }}
          onFulfill={(code) => console.log(code)}
          cellBorderWidth={1.5}
        />
        <DropDownPicker
          items={data}
          containerStyle={{ height: 40 }}
          style={{ backgroundColor: '#fafafa' }}
          itemStyle={{
            justifyContent: 'flex-start',
          }}
          dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
          placeholder='בחר קבוצה'
        />

        <Button mode='contained' style={styles.button}>
          {send_btn}
        </Button>

        <View style={styles.row}>
          <Text style={styles.label}>{no_code}</Text>
          <TouchableOpacity
            onPress={() => this.props.navigation.navigate('Login')}
          ></TouchableOpacity>
        </View>
      </KeyboardAwareScrollView>
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
    // alignItems: 'center',
    // justifyContent: 'center',
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
});

const mapStateToProps = (state) => {
  ///const { } = state.auth;
  return {};
};

export default connect(mapStateToProps)(AuthForm);
