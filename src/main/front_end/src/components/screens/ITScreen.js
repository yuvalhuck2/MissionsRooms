import React, { Component } from 'react';
import { StyleSheet, Text, Dimensions, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged, registerUser } from '../../actions';
import { theme } from '../../core/theme';
import { registerStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { Icon } from 'react-native-elements'
const {
  header,
  enter_email,
  enter_password,
  register_btn,
  already_user,
  already_user_sign_in,
} = registerStrings;

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';
const buttonColor = '#9932cc';
class ITForm extends Component {
  constructor(...args) {
    super(...args);
  }


  render() {
    const { email, password } = this.props;
    
    return (
      <View style={styles.container}>
        <View>
          <Button style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} />
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]} />
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]} />
        </View>
        <View>
          <Button  mode="contained" style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>CSV</Text> 
            <Icon name='rowing' />
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]} />
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]} />
        </View>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    flexWrap: 'wrap',
    marginTop: 0,
    backgroundColor: backgroundColor
  },
  button: {width: DeviceWidth*0.45,
     height: DeviceWidth*0.45,
     borderStyle: 'solid',
     borderWidth: 1,
     borderColor: 'black',
     backgroundColor: backgroundColor,
     textAlign: 'left',
     alignContent: 'center',
     justifyContent: 'center'
     
    },
    bottom_button_marg: {
        marginBottom:0,
        marginLeft:0,
        marginTop: 0,
    },
    top_button_marg: {
        marginBottom:0,
        marginLeft:0,
        marginTop: 30,
    },
    left_button_border: {
      borderLeftColor: backgroundColor
    },
    right_button_border: {
      borderRightColor: backgroundColor
    },
    top_button_border: {
      borderTopColor: backgroundColor
    },
    bottom_button_border :{
      borderBottomColor: backgroundColor
    }
});

const mapStateToProps = (state) => {
  return { };
};

export default connect(mapStateToProps)(ITForm);
