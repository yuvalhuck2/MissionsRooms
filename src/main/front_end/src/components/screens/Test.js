import React, { Component } from 'react';
import { StyleSheet, Text, View, Dimensions} from 'react-native';
import { Drawer,Button } from 'react-native-paper';
import { connect } from 'react-redux';
import { Icon } from 'react-native-elements'


const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';

class TestForm extends Component {
    
    constructor(...args) {
      super(...args);
    }

    render() {
    return (
        <View style={styles.container}>
        <View>
          <Button style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>1</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>
            <Text style={{color:"white"}}>2</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>
            <Text style={{color:"white"}}>2</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>
            <Text style={{color:"white"}}>2</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>
            <Text style={{color:"white"}}>2</Text>
          </Button> 
          <Button style={[styles.button, styles.bottom_button_marg, styles.left_button_border, styles.bottom_button_border]} >
            <Text style={{color:"white"}}>3</Text> 
            <Icon name='exit-to-app' />
          </Button>
        </View>
        <View>
          <Button  mode="contained"style={[styles.button, styles.top_button_marg, styles.right_button_border, styles.top_button_border]} >
            <Text style={{color:"white"}}>4</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}>
            <Text style={{color:"white"}}>5</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}>
            <Text style={{color:"white"}}>5</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}>
            <Text style={{color:"white"}}>5</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border]}>
            <Text style={{color:"white"}}>5</Text> 
          </Button>
          <Button style={[styles.button, styles.bottom_button_marg, styles.right_button_border, styles.bottom_button_border]} />
        </View>

      </View>
    //     <View>
    //         <Drawer.Section title="Some title">
    //   <Drawer.Item
    //     label="First Item"
    //     active={true}
    //     icon="star"
    //     onPress={() => {}}
    //   />
    //   <Drawer.Item
    //     label="Second Item"
    //     active={false}
    //     onPress={() => {}}
    //   />
    // </Drawer.Section>
    //     </View>
      
    )
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
       height: DeviceWidth*0.28,
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
    return {};
  };
  
  export default connect(mapStateToProps,{
  })(TestForm);
