import React, { Component } from 'react';
import { StyleSheet, Text, Dimensions, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import { StudentStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { Icon } from 'react-native-elements'
import * as NavPaths from '../../navigation/NavPaths'
import {passToMyRooms,logout,changeDialog, passToWatchProfiles, passToWatchMessages} from '../../actions'
import { Dialog,Paragraph,Portal } from 'react-native-paper';

const {
  watchMyRoom,
  main_screen,
  watchProfiles,
  watch_messages,
} = StudentStrings;

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';
const buttonColor = '#9932cc';

class StudentForm extends Component {
  constructor(...args) {
    super(...args);
    this.navigate=this.navigate.bind(this);
    this.onLogout=this.onLogout.bind(this);
    this.exitDailog=this.exitDailog.bind(this);
  }

  navigate(screen){
    const { navigation } = this.props;
    navigation.navigate(screen)
  }

  onLogout(){
    const {navigation}=this.props;
    return this.props.logout(navigation);
  }

  exitDailog(){
    return this.props.changeDialog("")
  }

  renderDialog(){
    const {dialog}=this.props;
    const visible= (dialog !="") 
    return (
      <Portal>
        <Dialog visible={visible} onDismiss={()=>this.exitDailog()}>
          <Dialog.Content>
            <Paragraph>{dialog}</Paragraph>
          </Dialog.Content>
        </Dialog>
      </Portal>
    )
  }

  render() {
    const { navigation,apiKey,rooms } = this.props;
    return (
      <View style={styles.container}>
        <View>
          <Button onPress={()=>this.props.passToMyRooms({navigation,apiKey,rooms})} style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} >
          <Text style={{color:"white"}}> {watchMyRoom}</Text> 
            <Icon name='meeting-room' />
          </Button>
          <Button  style={[styles.button, styles.bottom_button_marg, styles.left_button_border]} 
            onPress={()=>this.props.passToWatchProfiles({navigation,apiKey})}>
            <Text style={{color:"white"}}>{watchProfiles}</Text> 
          </Button>
          <Button  style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]}/>
        </View>
        <View>
          <Button mode="contained" style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}></Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]} 
            onPress={()=>this.props.passToWatchMessages({navigation,apiKey})}>
            <Text style={{color:"white"}}>{watch_messages}</Text> 
          </Button>
          <Button onPress={this.onLogout} style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]} >
          <Text style={{color:"white"}}>{main_screen}</Text> 
            <Icon name='exit-to-app' />
          </Button>
        </View>
        {this.renderDialog()}
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
  button: {width: DeviceWidth*0.5,
     height: DeviceWidth*0.45,
     borderStyle: 'solid',
     borderWidth: 1,
     borderColor: 'black',
     backgroundColor: backgroundColor,
     textAlign: 'left',
     alignContent: 'center',
     justifyContent: 'center',
     
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
  const { apiKey,rooms,dialog } = state.ChooseStudentRoom;
  return { apiKey,rooms,dialog };
};

export default connect(mapStateToProps,{
  passToMyRooms,
  logout,
  changeDialog,
  passToWatchProfiles,
  passToWatchMessages,
})(StudentForm);