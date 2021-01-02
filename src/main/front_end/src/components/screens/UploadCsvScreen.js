import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, Dimensions } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { connect } from 'react-redux';
import { emailChanged, passwordChanged } from '../../actions';
import { theme } from '../../core/theme';
import { uploadStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import * as DocumentPicker from 'expo-document-picker';

const {
    header,
    choose_btn,
    approve_btn,
} = uploadStrings;

const DeviceWidth = Dimensions.get('window').width;
class UploadCsvForm extends Component {
    files = []
    constructor(...args) {
        super(...args);
        this.state = { text: '' };
    }

    async onPickFile() {
        let res = await DocumentPicker.getDocumentAsync({
                type: "*/*",
                multiple: true,
                copyToCacheDirectory: true
            });
        // let [err, res] = DocumentPicker.getDocumentAsync({
        //     type: "*/*",
        //     multiple: true,
        //     copyToCacheDirectory: true
        // }).then(res => [null, res]).catch(err => [err, null]);
        // if (err) {
        //     alert("bad")
        //     alert(err)
        // } else {
            //alert("good");
            this.files.push(res)
            alert(res.name);
            console.log(res);
            this.setState({ text: this.state.text + " " + res.name })
        // }

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
                    onPress={() => console.log('there')}
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
