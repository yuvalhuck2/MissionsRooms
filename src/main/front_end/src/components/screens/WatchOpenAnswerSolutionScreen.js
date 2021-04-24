import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, Dimensions } from 'react-native';
import { connect } from 'react-redux';
import { uploadStrings, watchOpenSolution } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import { downloadFile } from '../../actions/WatchOpenAnswerActions'
import { theme } from '../../core/theme';


const {
    header,
    download_btn,
    question_title,
    answer_title
} = watchOpenSolution;

const DeviceWidth = Dimensions.get('window').width;
class WatchOpenAnswerForm extends Component {
    constructor(...args) {
        super(...args);
        this.onDownloadFile = this.onDownloadFile.bind(this);
    }


    onDownloadFile() {
        const { apiKey, roomId, missionId, fileName } = this.props;
        this.props.downloadFile( apiKey, roomId, missionId, fileName);
    }

    renderButton() {
        const { hasFile } = this.props

        return hasFile ?
            (<Button
                mode='contained'
                style={styles.button}
                onPress={this.onDownloadFile}>
                {download_btn}
            </Button>) : null

    }

    renderQuestion() {
        const { question } = this.props;
        return ([<Text>{question_title}</Text>,
        <Text>{question}</Text>])
    }

    renderOpenText() {
        const { openText } = this.props;
        return (<Text>{openText}</Text>)
    }

    renderTitle(title) {
        return (<Text style={styles.title}>{title}</Text>)
    }

    renderText(text) {
        return (<Text style={styles.text}>{text}</Text>)
    }

    render() {
        const { question, openText } = this.props;
        return (
            <View contentContainerStyle={styles.wrapper} style={styles.container}>
                <Header>{header}</Header>
                <View style={styles.row}>
                    <View >
                        {this.renderTitle(question_title)}
                        {this.renderText(question)}
                        {this.renderTitle(answer_title)}
                        {this.renderText(openText)}
                    </View>

                </View>
                <View style={styles.row_center}>
                    {this.renderButton()}
                </View>
            </View>
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
    },
    row: {
        flex: 1,
        marginTop: 0,
        justifyContent: 'flex-start',
        alignItems: 'flex-end',
        marginTop: 20,
        marginRight: 20
    },
    row_center: {
        flex: 1,
        marginTop: 0,
        justifyContent: 'center',
        alignSelf: 'center',
        marginTop: 0
    },
    button: {
        width: DeviceWidth * 0.5,
        height: DeviceWidth * 0.12,
    },
    wrapper: {
        justifyContent: 'center',
        alignItems: 'center'
    },
    errorTextStyle: {
        fontSize: 22,
        alignSelf: 'center',
        color: 'red',
    },
    title: {
        marginTop: 0,
        marginBottom: 10,
        fontSize: 20,
        textAlign: 'right',
        color: theme.colors.primary,
        textDecorationLine: 'underline'
    },
    text: {
        marginTop: 0,
        marginBottom: 5,
        fontSize: 15,
        textAlign: 'right',
    }
});

const mapStateToProps = (state) => {
    const { apiKey, roomId, missionId, openText, question, hasFile } = state.WatchOpenAnswer
    return { apiKey, roomId, missionId, openText, question, hasFile };
};

export default connect(mapStateToProps,
    { downloadFile })(WatchOpenAnswerForm);
