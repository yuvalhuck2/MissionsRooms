import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, Dimensions } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator, Appbar } from 'react-native-paper';
import { connect } from 'react-redux';
import { answerChanged } from '../../actions/SolveDeterministicActions';
import { sendOpenQuestionAnswer, onPickFile } from '../../actions/SolveOpenQuestionActions'
import { handleBack } from '../../actions/ChooseStudentRoomActions';
import { theme } from '../../core/theme';
import { SolveDeterministicMissionStrings, uploadStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { onRestart } from '../../actions'

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
        const { mission } = this.props;
        return (
            <KeyboardAwareScrollView style={styles.container}>
                <Appbar.Header>
                    <Appbar.BackAction onPress={() => { this.onBackPress() }} />
                </Appbar.Header>
                <Header>{mission.question}</Header>
                {this.renderError()}
                {this.renderTextBox()}
                {this.renderPickFile()}
                {this.renderButton()}
            </KeyboardAwareScrollView>
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
    }
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
})(SolveOpenQuestionMissionForm);
