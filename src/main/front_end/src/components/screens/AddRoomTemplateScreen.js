import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator,RadioButton } from 'react-native-paper';
import { AddRoomTempalteStrings,roomTypes } from '../../locale/locale_heb';
import { nameChanged,typeChanged,minimalMissionsChanged,passToMissions } from '../../actions';
import { Dropdown } from 'react-native-material-dropdown';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
    header,
    enter_name,
    enter_type,
    enter_minimal_amount,
    move_to_missions,
  } = AddRoomTempalteStrings;

const RoomTypes=roomTypes;

class AddTemplateForm extends Component{
    constructor(... args){
        super(... args)
        this.onNameChanged=this.onNameChanged.bind(this)
        this.onTypeChanged=this.onTypeChanged.bind(this)
        this.onMinimalAmountChanged=this.onMinimalAmountChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
    }

    onNameChanged(text){
        this.props.nameChanged(text);
    }

    onTypeChanged(text) {
        this.props.typeChanged(text);
    }
    
    onMinimalAmountChanged(amount){
        this.props.minimalMissionsChanged(amount);
    }

    onButtonPress() {
        const {name,minimalMissions,type} = this.props;
        this.props.passToMissions( name,minimalMissions,type );
    }

    renderSpinner() {
        return <ActivityIndicator animating={true} color={theme.colors.primary} size='large'/>;
    }

    renderButton(){
        const {loading} = this.props
    
        return loading ? (
          this.renderSpinner()
        ) : (
          <Button
          mode='contained'
          style={styles.button}
          onPress={this.onButtonPress}
          >
          {move_to_missions}
          </Button>
        )
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

      renderRadioButtons(){
          let lst=[]
          RoomTypes.map((roomType)=>{
            lst.push(
                <RadioButton.Item 
                    label={roomType.translate}
                    //onPress={()=>this.onTypeChanged(roomType.type)}
                    //status={roomType.type===type ? 'checked' : 'unchecked'}
                    value={roomType.type}
                 />)
          })
          
          return lst
        //   return(
        //     roomTypes.map((roomType)=>{
        //         <RadioButton.Item label='d' value='u' />
        //     }
        // // [<RadioButton.Item label="First item" value="first" />,
        // // <RadioButton.Item label="Second item" value="second" />]
        //   )
        //   )
      }

    render(){
        const {name,minimalMissions,type}=this.props;
        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            <TextInput
            label={enter_name}
            value={name}
            onChangeText={this.onNameChanged}
            placeholder='שם'
            />

            <TextInput
            label={enter_minimal_amount}
            keyboardType = 'numeric'
            value={minimalMissions}
            onChangeText={this.onMinimalAmountChanged}
            placeholder='0'
            />

            <RadioButton.Group 
            onValueChange={(value) => this.onTypeChanged(value)} 
            value={type}>
                {this.renderRadioButtons()}
            </RadioButton.Group>
            {this.renderButton()}
            {this.renderError()}
        </KeyboardAwareScrollView>
    );
        
    }
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      padding: 20,
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
      marginTop: 10,
    },
    link: {
      fontWeight: 'bold',
      color: theme.colors.primary,
    },
    errorTextStyle: {
      fontSize: 25,
      alignSelf: 'center',
      color: theme.colors.error,
    },
  });

const mapStateToProps = (state) => {
    const { name,minimalMissions,type, loading, errorMessage } = state.addMission;
    return { name,minimalMissions,type, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    nameChanged,
    minimalMissionsChanged,
    typeChanged,
    passToMissions,
  })(AddTemplateForm);