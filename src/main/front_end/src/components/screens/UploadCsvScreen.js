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
    files = []
    constructor(...args) {
        super(...args);
        this.state = { text: '' };
    }

    async onRestart(){
        this.file = []
        this.setState({text: ""})
    }

    async onPickFile() {
        let res = DocumentPicker.getDocumentAsync({
            type: 'text/comma-separated-values',
            multiple: true,
            copyToCacheDirectory: true
        }).then(res => {
            if (res != undefined && res != null && res.type != 'cancel') {
                this.files.push(res)
                this.setState({ text: this.state.text + " " + res.name })
            }
        }).catch(err => alert("Document Picker Error"));
    }

    async onSendFiles() {
        const formData = new FormData();
        if (this.files.length != 4){
            alert(file_number_error)
            return
        }
        this.files.forEach(file => {
            formData.append("files", {
                uri: Platform.OS === 'android' ? file.uri : file.uri.replace('file://', ''),
                type: "*/*",
                name: file.name
            });
        });  
        const xhr = new XMLHttpRequest();
        // 2. open request
        xhr.open('POST', 'http://192.168.1.14:8080/uploadCsv?token=abc');
        //Send the proper header information along with the request
        xhr.setRequestHeader('Content-type', 'multipart/form-data');
        // 3. set up callback for request
        xhr.onload = () => {
            const response = JSON.parse(xhr.response);
            //TODO: parse by code
            alert(response);
            // ... do something with the successful response
        };
        // 4. catch for request error
        xhr.onerror = e => {
            alert('upload failed');
        };
        // 4. catch for request timeout
        xhr.ontimeout = e => {
            alert('upload timeout');
        };


        xhr.onreadystatechange = function () {//Call a function when the state changes.
            alert(xhr.responseText);
        }

        xhr.send(formData);

        this.files = []
        this.setState({ text: "" })


    }

    render() {
        const { email, password } = this.props;

        return (
            <KeyboardAwareScrollView contentContainerStyle={styles.wrapper} style={styles.container}>
                <Header>{header}</Header>
                <View style={styles.row}>
                    <TextInput style={styles.email_label}
                        editable={false}
                        value={this.state.text}
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
    return {};
};

export default connect(mapStateToProps, {
})(UploadCsvForm);
