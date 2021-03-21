import React, { Component } from 'react';
import { StyleSheet, Text, View, Dimensions} from 'react-native';
import Header from '../common/Header';
import { Button, DataTable, Appbar, Switch } from 'react-native-paper';
import { connect } from 'react-redux';
import TextInput from '../common/TextInput';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';

const DeviceWidth  = Dimensions.get('window').width;
const backgroundColor = 'purple';

class TestForm extends Component {
    
    constructor(...args) {
      super(...args);
    }

    render() {
    return (
      <KeyboardAwareScrollView>
        <DataTable>
        <DataTable.Row>
        <DataTable.Cell> vera254</DataTable.Cell>
          <Button key = "button" mode="outlined"
              compact = {true} onPress={() => {}}>
              {"מחיקה"}
            </Button>
            <DataTable.Cell></DataTable.Cell>
            <Button key = "button" mode="outlined"
              compact = {true} onPress={() => {}}>
              {"עריכה"}
            </Button>
            <DataTable.Cell></DataTable.Cell>
            <Button key = "button" mode="outlined"
              compact = {true} onPress={() => {}}>
              {"העברה"}
            </Button>
        </DataTable.Row>
        {/* <DataTable.Row>
        <DataTable.Cell>היי</DataTable.Cell>
          <Switch value={true} onValueChange={()=>{}} />
          <DataTable.Cell>היי</DataTable.Cell>
          <Switch value={true} onValueChange={()=>{}} />
          <DataTable.Cell>היי</DataTable.Cell>
          <Switch value={true} onValueChange={()=>{}} />
        </DataTable.Row>
        <DataTable.Row>
        <DataTable.Cell>היי</DataTable.Cell>
          <Switch value={true} onValueChange={()=>{}} />
        </DataTable.Row>
        <DataTable.Row>
          <DataTable.Cell>היי</DataTable.Cell>
          <Switch  value={true} onValueChange={()=>{}} />
        </DataTable.Row> */}
        </DataTable>
        {/* <Switch value={true} onValueChange={()=>{}}><Text>4</Text></Switch>
        <Switch value={true} onValueChange={()=>{}}><Text>1</Text></Switch> */}
        <TextInput 
          mode='outlined'
          label="Label Name"
          placeholder="Enter placeholder"
            />
        {/* <Appbar.Header styles={styles.bottom}>
           <Appbar.BackAction onPress={() => console.log('Pressed')} />
         </Appbar.Header>
          <DataTable style = {styles.container}>
          <Header>טבלת השיאים</Header>
          <DataTable.Row>
            <Button style = {styles.tabButton} icon="account-group" mode="text" disabled = {false} onPress={() => console.log('Pressed')}>
                כיתתי
            </Button>
            <Button style = {styles.tabButton} icon="account-multiple" mode="text" disabled = {false} onPress={() => console.log('Pressed')}>
                קבוצתי
            </Button>
            <Button style = {styles.tabButton} icon="account" mode="contained" disabled = {false} onPress={() => console.log('Pressed')}>
                אישי
            </Button>
          </DataTable.Row>
          <DataTable.Row>
          <TextInput 
            label='הזנת מספר נקודות אשר ברצונך להוריד'
            keyboardType = 'numeric'
            onChangeText = {() => console.log('Pressed')}
            value = {'3'}
          /> 
          </DataTable.Row>
          <DataTable.Row></DataTable.Row>
          <DataTable.Row>
          <DataTable.Title>מקום</DataTable.Title>
          <DataTable.Title>שם</DataTable.Title>
          <DataTable.Title>נקודות</DataTable.Title>
          <DataTable.Title numeric>הורדת נקודות</DataTable.Title>
        </DataTable.Row>
            <DataTable.Row>
            <DataTable.Cell >1</DataTable.Cell>
              <DataTable.Cell>שלום</DataTable.Cell>
              <DataTable.Cell numeric>2</DataTable.Cell>
              <DataTable.Cell></DataTable.Cell>
              <Button icon="thumb-down" mode="outlined" disabled = {false} compact = {true} onPress={() => console.log('Pressed')}>
              מינוס 3
              </Button>
            </DataTable.Row>
            <DataTable.Row>
            <DataTable.Cell>2</DataTable.Cell>
              <DataTable.Cell>שמואל</DataTable.Cell>
              <DataTable.Cell numeric>2</DataTable.Cell>
              <DataTable.Cell></DataTable.Cell>
              <Button icon="thumb-down" mode="outlined" disabled = {false} compact = {true} onPress={() => console.log('Pressed')}>
                מינוס 3
              </Button>
                {/* <IconButton
                  icon="camera"
                  color={Colors.red500}
                  size={20}
                  onPress={() => console.log('Pressed')}
                /> }
            </DataTable.Row>
          </DataTable> */}
         </KeyboardAwareScrollView>
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
    tabButton: {width: DeviceWidth*0.31 },
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
