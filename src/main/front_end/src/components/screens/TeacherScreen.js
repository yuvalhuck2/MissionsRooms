import React, { Component } from 'react';
import { StyleSheet, Text, Dimensions, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import { TeacherStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { Icon } from 'react-native-elements'
import * as NavPaths from '../../navigation/NavPaths'
const {
  addMission,
  addTemplate,
  createRoom,
  closeRoom,
} = TeacherStrings;

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';
const buttonColor = '#9932cc';

class TeacherForm extends Component {
  constructor(...args) {
    super(...args);
    this.navigate=this.navigate.bind(this);
  }

  navigate(screen){
    const { navigation } = this.props;
    navigation.navigate(screen)
  }


  render() {
    
    return (
      <View style={styles.container}>
        <View>
          <Button onPress={()=>this.navigate(NavPaths.addMissionScreen)} style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} >
          <Text style={{color:"white"}}> {addMission}</Text> 
            <Icon name='create' />
          </Button>
          <Button onPress={()=>this.navigate(NavPaths.addTemplate)} style={[styles.button, styles.bottom_button_marg, styles.left_button_border]} >
          <Text style={{color:"white"}}>{addTemplate}</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]} />
        </View>
        <View>
          <Button  onPress={()=>this.navigate(NavPaths.addTemplate)} mode="contained" style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>{createRoom}</Text> 
            <Icon name='create' />
          </Button>
          <Button onPress={()=>this.navigate(NavPaths.addTemplate)} style={[styles.button, styles.bottom_button_marg, styles.right_button_border]} >
            <Text style={{color:"white"}}>{closeRoom}</Text> 
            <Icon name='delete' />
          </Button>
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
  return { };
};

export default connect(mapStateToProps)(TeacherForm);