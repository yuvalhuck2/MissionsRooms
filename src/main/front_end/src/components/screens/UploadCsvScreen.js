import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, Dimensions } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged } from '../../actions';
import { uploadStrings, uploadStringsErrors } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import * as DocumentPicker from 'expo-document-picker';
import { Platform } from 'react-native';
import {onRestart, onPickFile, onSendFiles } from '../../actions'

const {
    header,
    choose_btn,
    approve_btn,
    restart_btn
} = uploadStrings;

const {
    file_number_error
} = uploadStringsErrors

const DeviceWidth = Dimensions.get('window').width;
class UploadCsvForm extends Component {
    constructor(...args) {
        super(...args);
        this.onPickFile = this.onPickFile.bind(this)
        this.onRestart = this.onRestart.bind(this)
        this.onSendFiles = this.onSendFiles.bind(this)
    }

    onRestart(){
        this.props.onRestart()
    }

    onPickFile() {
        this.props.onPickFile()
    }

    async onSendFiles() {
        this.props.onSendFiles({files: this.props.files})
    }

    render() {
        const { text } = this.props;

        return (
            <KeyboardAwareScrollView contentContainerStyle={styles.wrapper} style={styles.container}>
                <Header>{header}</Header>
                <View style={styles.row}>
                    <TextInput style={styles.email_label}
                        editable={false}
                        value={text}
                    />
                    <Button style={styles.button}
                        mode='contained'
                        onPress={this.onPickFile.bind(this)}
                    >
                        {choose_btn}
                    </Button>
                </View>
                <Button style={styles.button}
                    mode='contained'
                    onPress={this.onRestart.bind(this)}
                >
                    {restart_btn}
                </Button>
                <Button style={styles.button}
                    mode='contained'
                    onPress={this.onSendFiles.bind(this)}
                >
                    {approve_btn}
                </Button>
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
    },
    row: {
        flex: 1,
        marginTop: 50,
        justifyContent: 'center',
        alignItems: 'center',
    },
    email_label: {
        width: DeviceWidth * 0.8
    },
    button: {
        width: DeviceWidth * 0.3,
        height: DeviceWidth * 0.1,
    },
    wrapper: {
        justifyContent: 'center',
        alignItems: 'center'
    },
});

const mapStateToProps = (state) => {
    const {text, files} = state.IT
    return {text, files};
};

export default connect(mapStateToProps, 
    { onRestart, onPickFile, onSendFiles })(UploadCsvForm);