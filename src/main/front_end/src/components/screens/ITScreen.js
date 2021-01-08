import React, { Component } from 'react';
import { StyleSheet, Text, Dimensions, View } from 'react-native';
import { connect } from 'react-redux';
import Button from '../common/Button';
import { Icon } from 'react-native-elements'
import  { navigateToUploadCSV } from '../../actions'
import { ITStrings } from '../../locale/locale_heb';
import {logout} from '../../actions'

const {
  uploadCSV,
  main_screen
} = ITStrings

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';
const buttonColor = '#9932cc';

class ITForm extends Component {
  constructor(...args) {
    super(...args);
    this.navigateToUploadCSV = this.navigateToUploadCSV.bind(this);
    this.onLogout=this.onLogout.bind(this);
  }

  navigateToUploadCSV() {
    const { navigation } = this.props;
    this.props.navigateToUploadCSV({ navigation });
  }

  onLogout(){
    const {navigation}=this.props;
    return this.props.logout(navigation);
  }


  render() {
    
    return (
      <View style={styles.container}>
        <View>
          <Button style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} />
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]} />
          <Button onPress={this.onLogout} style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]} >
            <Text style={{color:"white"}}>{main_screen}</Text> 
            <Icon name='exit-to-app' />
          </Button>
        </View>
        <View>
          <Button  mode="contained" onPress={this.navigateToUploadCSV} style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>{uploadCSV}</Text> 
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

export default connect(mapStateToProps, {navigateToUploadCSV, logout})(ITForm);
