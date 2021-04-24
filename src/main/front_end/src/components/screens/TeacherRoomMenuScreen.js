import React, { Component } from 'react';
import {Alert, StyleSheet, Text, TouchableOpacity, View} from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ProgressBar, RadioButton} from 'react-native-paper';
import {ChooseMissionsTemplateStrings, TeacherRoomMenuStrings} from '../../locale/locale_heb';
import { passToRoomList } from '../../actions/ChooseRoomTeacherActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import { Menu ,Card, Title, Paragraph} from 'react-native-paper';
import {DETERMINISTIC_NAME} from "../../actions/types";
import {closeRoom,enterChatTeacher} from '../../actions/ChooseRoomTeacherActions'

const {
    chat,
    approve_answer,
    close_room,
    mission,
    of,
    yes,
    no,sure
} = TeacherRoomMenuStrings;
const {
    deterministic_name,
    question,
} = ChooseMissionsTemplateStrings

class TeacherRoomMenuForm extends Component{
    constructor(... args){
        super(... args)
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


    getMissionPresentation(mission){
        if(mission!=null) {
            switch (mission.name) {
                case DETERMINISTIC_NAME:
                    return deterministic_name + '\n\t\t' + question + mission.question[0]
                default:
                    return ""
            }
        }
    }

    getProgressPresentation(room){
        return mission + ' ' + room.currentMissionNumber + ' ' + of + ' ' + room.numberOfMissions;

    }


    render(){
        const {navigation,apiKey ,roomsType,currentRoom}=this.props;
        console.log(currentRoom);
        //if(currentRoom!=''){
        let progress = currentRoom.currentMissionNumber / currentRoom.numberOfMissions;
        return (
            <View style={styles.container}>
                <Card>
                    <Card.Content>
                        <Title>{currentRoom.name}</Title>
                        <Paragraph>{this.getProgressPresentation(currentRoom)}</Paragraph>
                        <ProgressBar progress={progress} color={theme.colors.primary}/>
                        <Paragraph>{this.getMissionPresentation(currentRoom.currentMission)}</Paragraph>

                    </Card.Content>
                </Card>
                <View style={{flex: 1}}>
                    <Menu.Item icon="chat" onPress={() => this.props.enterChatTeacher({
                        navigation,
                        apiKey,
                        roomId: currentRoom.roomId
                    })} title={chat}/>
                    <Menu.Item icon="answer" onPress={() => {
                    }} title={approve_answer}/>
                    <Menu.Item icon="close" onPress={() =>Alert.alert(sure,"",[
                        {text:yes,onPress:()=>this.props.closeRoom({navigation, apiKey, currentRoom})},
                        {text:no,onPress:()=>{}}
                    ]) }
                               title={close_room}/>
                </View>
            </View>
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
        backgroundColor: theme.colors.primary,
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
    const { type,roomsType,currentRoom, apiKey, errorMessage } = state.ChooseTeacherRoomType;
    return { type,roomsType,currentRoom,apiKey, errorMessage };
};

export default connect(mapStateToProps, {
    closeRoom,
    enterChatTeacher,
})(TeacherRoomMenuForm);