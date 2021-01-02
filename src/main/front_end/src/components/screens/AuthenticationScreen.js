import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { connect } from 'react-redux';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { authStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { Dropdown } from 'react-native-material-dropdown';
import CodeInput from '@andreferi/react-native-confirmation-code-input';

const {
  header,
  enter_code,
  choose_group,
  send_btn,
  no_code,
} = authStrings;

class AuthForm extends Component {
    constructor(...args) {
      super(...args);
    }

    render() {
      const { code, group } = this.props;
      let data = [
        {      value: 'Banana',    },
        {      value: 'Mango',    },
        {      value: 'Pear',    }];
  
      return (
        <KeyboardAwareScrollView style={styles.container}>
          <Header>{header}</Header>


            <CodeInput
            ref="codeInputRef2"
            keyboardType="numeric"
            codeLength={5}
            activeColor='rgb(148,0,211)'
            inactiveColor='rgb(148,0,211)'
            className='border-circle'
            autoFocus={false}
            codeInputStyle={{ fontWeight: '800' }}
            containerStyle={{ marginTop: 30, marginBottom: 30 }}
            codeInputStyle={{ borderWidth: 1.5 }}
            onFulfill={(code) => console.log(code)}
             />
  
          <Dropdown
            label={choose_group}
            style={styles.button}
            data={data} 
          />
  
          <Button
            mode='contained'
            style={styles.button}
          >
            {send_btn}
          </Button>
  
          <View style={styles.row}>
            <Text style={styles.label}>{no_code}</Text>
            <TouchableOpacity
              onPress={() => this.props.navigation.navigate('Login')}
            >
            </TouchableOpacity>
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
      marginTop: 24,
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