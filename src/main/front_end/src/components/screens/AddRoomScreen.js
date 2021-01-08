import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator } from 'react-native-paper';
import { AddRoomStrings,roomTypes } from '../../locale/locale_heb';
import { nameChanged,bonusChanged,studentChanged,groupChanged,passToTemplates } from '../../actions/AddRoomActions';
import DropDownPicker from 'react-native-dropdown-picker';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
    header,
    enter_name,
    enter_bonus,
    enter_student,
    enter_group,
    classroom_add,
    group_add,
    student_add,
  } = AddRoomStrings;

const RoomTypes=roomTypes.map((x)=>x.type);

class AddRoomForm extends Component{
    constructor(... args){
        super(... args)
        this.onNameChanged=this.onNameChanged.bind(this)
        this.onBonusChanged=this.onBonusChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
        this.onGroupChanged=this.onGroupChanged.bind(this)
        this.onStudentChanged=this.onStudentChanged.bind(this)
    }

    onNameChanged(text){
        this.props.nameChanged(text);
    }

    onBonusChanged(text) {
        this.props.bonusChanged(text);
    }

    onStudentChanged(student){
        this.props.studentChanged(student.value);
    }

    onGroupChanged(group){
        this.props.groupChanged(group.value);
    }

    onButtonPress(type) {
        const {roomName,bonus,classroom,group,student,allTemplates,navigation} = this.props;
        this.props.passToTemplates ({roomName,bonus,classroom:classroom.name,group,student,allTemplates,type,navigation} );
    }


    renderSpinner() {
        return <ActivityIndicator animating={true} color={theme.colors.primary} size='large'/>;
    }

    renderButton(name,type){
            
        return  (
          <Button
          mode='contained'
          style={styles.button}
          onPress={()=>this.onButtonPress(type)}
          >
          {name}
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

    render(){
        const {roomName,bonus,classroom}=this.props;
        return(
        <KeyboardAwareScrollView style={styles.container}>
            
            <Header>{header}</Header>
            {this.renderError()}
            <DropDownPicker
                items={classroom.groups.reduce(
                    (acc,g)=>{return acc.concat(g.students.map((s)=>{
                        return {label:s.firstName+' '+s.lastName, value: s.alias}
                    }
                    ))}
                    ,[])
                }
                containerStyle={{ height: 40 }}
                style={{ backgroundColor: '#fafafa' }}
                itemStyle={{
                  justifyContent: 'flex-start',
                }}
                dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
                onChangeItem={this.onStudentChanged}
                placeholder={enter_student}
            />
            <DropDownPicker
                items={classroom.groups.map(
                    (g)=>{return {label:g.groupType, value: g.name}}
                    )
                }
                containerStyle={{ height: 40 }}
                style={{ backgroundColor: '#fafafa' }}
                itemStyle={{
                  justifyContent: 'flex-start',
                }}
                dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
                onChangeItem={this.onGroupChanged}
                placeholder={enter_group}
            />
            <TextInput
            label={enter_name}
            value={roomName}
            onChangeText={this.onNameChanged}
            placeholder='שם'
            />

            <TextInput
            label={enter_bonus}
            keyboardType = 'numeric'
            value={bonus}
            onChangeText={this.onBonusChanged}
            placeholder='0'
            />
            {this.renderButton(classroom_add,RoomTypes[2])}
            {this.renderButton(group_add,RoomTypes[1])}
            {this.renderButton(student_add,RoomTypes[0])}
            
            
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
    const { roomName, bonus,classroom,group,student,allTemplates, loading, errorMessage } = state.addRoom;
    return { roomName,bonus,classroom,group,student,allTemplates, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    nameChanged,
    bonusChanged,
    passToTemplates,
    studentChanged,
    groupChanged,
  })(AddRoomForm);