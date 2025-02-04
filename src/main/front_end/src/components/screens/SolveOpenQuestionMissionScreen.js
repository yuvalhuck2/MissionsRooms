import React, { Component } from 'react';
import { Dimensions, SafeAreaView, StyleSheet, Text, View } from 'react-native';
import { ActivityIndicator } from 'react-native-paper';
import { connect } from 'react-redux';
import { onRestart } from '../../actions';
import { enterChatStudent, handleBack } from '../../actions/ChooseStudentRoomActions';
import { answerChanged } from '../../actions/SolveDeterministicActions';
import { onPickFile, sendOpenQuestionAnswer } from '../../actions/SolveOpenQuestionActions';
import { theme } from '../../core/theme';
import { SolveDeterministicMissionStrings, uploadStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import CustomAppbar from "../common/CustomAppbar";
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
    enter_answer,
    send_answer,
} = SolveDeterministicMissionStrings;

const {
    choose_btn,
    restart_btn,
} = uploadStrings;
const DeviceWidth = Dimensions.get('window').width;
class SolveOpenQuestionMissionForm extends Component {
    constructor(...args) {
        super(...args);
        this.onAnswerChanged = this.onAnswerChanged.bind(this);
        this.onButtonPress = this.onButtonPress.bind(this);
        this.onBackPress = this.onBackPress.bind(this);
        this.onPickFile = this.onPickFile.bind(this)
        this.onRestart = this.onRestart.bind(this)
        this.onChatButtonPress=this.onChatButtonPress.bind(this);
    }

    onAnswerChanged(text) {
        this.props.answerChanged(text);
    }

    onButtonPress() {
        const { roomId, mission, file, apiKey, currentAnswer } = this.props;
        this.props.sendOpenQuestionAnswer({ roomId, missionId: mission.missionId, file, apiKey, currentAnswer });
    }

    onBackPress() {
        const { navigation, apiKey, roomId, mission } = this.props;
        this.props.handleBack({ navigation, apiKey, roomId, missionId: mission.missionId });
    }

    onChatButtonPress() {
        const {navigation} = this.props;
        this.props.enterChatStudent({navigation});
    }


    onRestart() {
        this.props.onRestart()
    }

    onPickFile() {
        this.props.onPickFile()
    }


    renderSpinner() {
        return (
            <ActivityIndicator
                animating={true}
                color={theme.colors.primary}
                size='large'
            />
        );
    }

    renderButton() {
        const { loading, isInCharge } = this.props

        return loading ? (
            this.renderSpinner()
        ) : (
            isInCharge ?
                    (<Button
                        mode='contained'
                        style={styles.button}
                        onPress={this.onButtonPress}>
                        {send_answer}
                    </Button>) : null
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

    renderTextBox() {
        const { currentAnswer, isInCharge } = this.props;
        if (isInCharge) {
            return (
                <TextInput
                    label={enter_answer}
                    value={currentAnswer}
                    onChangeText={this.onAnswerChanged}
                    placeholder='תשובה'
                />
            )
        }
    }

    renderPickFile() {
        const { fileText, isInCharge } = this.props;
        if (isInCharge) {
            return (
                <View style={styles.row}>
                    <TextInput 
                        editable={false}
                        value={fileText}
                    />
                    <Button style={styles.button1}
                        mode='contained'
                        onPress={this.onPickFile.bind(this)}
                    >
                        {choose_btn}
                    </Button>
                    <Button style={styles.button1}
                        mode='contained'
                        onPress={this.onRestart.bind(this)}
                    >
                        {restart_btn}
                    </Button>
                </View>)

        }

    }

    render() {
        const { mission, isInCharge } = this.props;
        console.log(isInCharge)
        console.log(mission)
        return (
            <SafeAreaView style={styles.container}>
        <CustomAppbar
          backAction={this.onBackPress}
          actions={[{ icon: "chat", onPress: this.onChatButtonPress }]}
        />
                <Header>{mission ? mission.question[0]: ""}</Header>
                {this.renderError()}
                {this.renderTextBox()}
                {this.renderPickFile()}
                {this.renderButton()}
            </SafeAreaView>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 0,
        width: '100%',
        maxWidth: 340,
        alignSelf: 'center',
        // alignItems: 'center',
        // justifyContent: 'center',
    },
    button: {
        marginTop: 0,
    },
    button1: {
        width: DeviceWidth * 0.5,
        height: DeviceWidth * 0.12,
    },
    //   row: {
    //     flexDirection: 'row',
    //     marginTop: 10,
    //   },
    row: {
        flex: 1,
        marginTop: 0,
        justifyContent: 'center',
        alignItems: 'center',
    },
    link: {
        fontWeight: 'bold',
        color: theme.colors.primary,
    },
    errorTextStyle: {
        fontSize: 22,
        alignSelf: 'center',
        color: theme.colors.error,
    },
    files_label: {
        width: DeviceWidth,
    },
    chatButton:{
        //alignSelf: 'flex-end',
        position: 'absolute',
        top:550,
        right:0,
    },
});

const mapStateToProps = (state) => {
    const { fileText, file, roomId, loading, mission, currentAnswer, apiKey, isInCharge, errorMessage } = state.SolveOpenQuestion;
    return { roomId, loading, mission, currentAnswer, apiKey, isInCharge, errorMessage, fileText, file };
};


export default connect(mapStateToProps, {
    onRestart, onPickFile,
    answerChanged,
    sendOpenQuestionAnswer,
    handleBack,
    enterChatStudent,
})(SolveOpenQuestionMissionForm);
