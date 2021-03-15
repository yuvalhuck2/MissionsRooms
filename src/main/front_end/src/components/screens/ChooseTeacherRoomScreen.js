import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { RadioButton } from 'react-native-paper';
import { ChooseTeacherRoomStrings,roomTypes } from '../../locale/locale_heb';
import { roomChanged,passToRoomMenu } from '../../actions/ChooseRoomTeacherActions.js';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import { DETERMINISTIC_NAME } from '../../actions/types';
import ChooseTeacherRoomTypeScreen from "./ChooseTeacherRoomTypeScreen";

const {
    header,
    room_name,
    solve,
    no_rooms,
} = ChooseTeacherRoomStrings;



class ChooseClassroomRoomForm extends Component{
    constructor(... args){
        super(... args)
        this.onRoomChanged=this.onRoomChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
    }

    onRoomChanged(value){
        this.props.roomChanged(value);
    }

    onButtonPress() {
        const {currentRoom,navigation,apiKey} = this.props;
        this.props.passToRoomMenu( {currentRoom,navigation,apiKey} );
    }

    renderButton(){
        const{apiKey,presentRooms}=this.props;
        if (presentRooms.roomDetailsDataList.length>0){
            return (
                <Button
                    mode='contained'
                    style={styles.button}
                    onPress={this.onButtonPress}
                >
                    {solve}
                </Button>
            )
        }
        else{
            return (
                <View>
                    <Text style={styles.errorTextStyle}>{no_rooms}</Text>
                </View>
            )
        }
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


    getType(type){
        return roomTypes.filter((roomType)=>roomType.type==type)[0].translate.slice(0,-1)
    }

    renderRoom(room){
        return room_name+room.name;
    }

    renderRadioButtons(){
        const {presentRooms}=this.props;
        let lst=[];
        if(presentRooms.roomDetailsDataList.length>0) {
            presentRooms.roomDetailsDataList.map((room) => {
                lst.push(
                    <RadioButton.Item
                        label={this.renderRoom(room)}
                        value={room}
                        color={theme.colors.primary}
                        labelStyle={{
                            fontWeight: 'bold',
                            color: theme.colors.secondary,
                        }}

                    />)
            });
        }
        return lst;
    }

    render(){
        const {currentRoom}=this.props;

        return (
            <KeyboardAwareScrollView style={styles.container}>
                <Header>{header}</Header>
                <RadioButton.Group
                    onValueChange={(value) => this.onRoomChanged(value)}
                    value={currentRoom}>
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
    const { currentRoom,roomsType, errorMessage , apiKey,presentRooms} = state.ChooseTeacherRoomType;
    return { currentRoom,roomsType, errorMessage ,apiKey,presentRooms};
};

export default connect(mapStateToProps, {
    roomChanged,
    passToRoomMenu,
})(ChooseClassroomRoomForm);