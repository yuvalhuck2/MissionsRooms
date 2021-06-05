import React, { Component } from 'react';
import {StyleSheet, Text, Dimensions, View, Alert, SafeAreaView} from 'react-native';
import { connect } from 'react-redux';
import Button from '../common/Button';
import { Icon } from 'react-native-elements'
import  { navigateToUploadCSV, navigateToAddNewIT,passToWatchProfiles, logout,
  passToWatchMessages, passToManageUsers, passToAddStudent, passToAddTeacher,deleteSeniorStudents } from '../../actions'
import { ITStrings, AllUsersStrings,DeleteUserString  } from '../../locale/locale_heb';
import * as NavPaths from '../../navigation/NavPaths'

const {
  uploadCSV,
  addNewIT,
  manageUsers,
  add_teacher,
  add_student,
  close_classroom,
  delete_senior,
} = ITStrings

const {
  changePassword,
  watchProfiles,
  watch_messages,
  main_screen,
} = AllUsersStrings

const {
  sureSeniors,
    yes, no,
}=DeleteUserString

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';
const buttonColor = '#9932cc';

class ITForm extends Component {
  constructor(...args) {
    super(...args);
    this.navigateToUploadCSV = this.navigateToUploadCSV.bind(this);
    this.onLogout=this.onLogout.bind(this);
    this.navigateToAddNewIT=this.navigateToAddNewIT.bind(this);
    this.onPressDeleteSenior=this.onPressDeleteSenior.bind(this);
  }

  navigateToUploadCSV() {
    const { navigation } = this.props;
    this.props.navigateToUploadCSV({ navigation });
  }

  onLogout(){
    const {navigation}=this.props;
    return this.props.logout(navigation);
  }

  navigateToAddNewIT(){
    const {navigation}=this.props;
    return this.props.navigateToAddNewIT({navigation});
  }

  onPressDeleteSenior(){
    const {apiKey,navigation}=this.props;
    return Alert.alert(sureSeniors,"",[
        {text:yes,onPress:()=>this.props.deleteSeniorStudents({apiKey,navigation})},
        {text:no,onPress:()=>{}}
    ]);
  }


  render() {
    const {navigation,apiKey} =this.props
    return (
      <SafeAreaView style={styles.container}>
        <View>
          <Button onPress={this.navigateToAddNewIT} style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>{addNewIT}</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}
            onPress={()=>this.props.passToManageUsers({navigation,apiKey})}>
            <Text style={{color:"white"}}>{manageUsers}</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}
            onPress={()=>this.props.passToAddStudent({navigation})}>
            <Text style={{color:"white"}}>{add_student}</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}
            onPress={()=>this.props.passToWatchProfiles({navigation,apiKey})}>
            <Text style={{color:"white"}}>{watchProfiles}</Text>
          </Button>
            <Button onPress={()=>this.onPressDeleteSenior()} style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]}>
                <Text style={{color:"white"}}>{delete_senior}</Text>
            </Button>
          <Button onPress={this.onLogout} style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]} >
            <Text style={{color:"white"}}>{main_screen}</Text> 
            <Icon name='exit-to-app' />
          </Button>
        </View>
        <View>
          <Button  mode="contained" onPress={this.navigateToUploadCSV} style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>{uploadCSV}</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}
            onPress={()=>this.props.passToAddTeacher({navigation})}>
            <Text style={{color:"white"}}>{add_teacher}</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}
            onPress={()=>navigation.navigate(NavPaths.closeClassroom)}>
            <Text style={{color:"white"}}>{close_classroom}</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}
            onPress={()=>this.props.passToWatchMessages({navigation,apiKey})}>
            <Text style={{color:"white"}}>{watch_messages}</Text> 
          </Button>
          <Button onPress={()=>navigation.navigate(NavPaths.changePassword)} style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]}>
            <Text style={{color:"white"}}>{changePassword}</Text> 
          </Button>
            <Button  style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]}>
                
            </Button>
        </View>

      </SafeAreaView>
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
     height: DeviceWidth*0.27,
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
  const {apiKey} = state.IT;
  return {apiKey};
};

export default connect(mapStateToProps, {
  navigateToUploadCSV,
  logout,
  navigateToAddNewIT,
  passToWatchProfiles,
  passToWatchMessages,
  passToManageUsers,
  passToAddStudent,
  passToAddTeacher,
    deleteSeniorStudents,
})(ITForm);
