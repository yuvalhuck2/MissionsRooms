import {StyleSheet} from "react-native";
import {theme} from "../../core/theme";
import {Appbar} from 'react-native-paper';
import {connect} from 'react-redux';
import {passwordChanged, changePassword, handleBack,} from "../../actions/ChangePasswordActions";
import {ChangePasswordStrings} from "../../locale/locale_heb";
import {Component} from "react";
import Button from "../common/Button";
import React from "react";
import {KeyboardAwareScrollView} from "react-native-keyboard-aware-scroll-view";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
    header,
    enter_new_password,
    change_password,
} = ChangePasswordStrings;


class ChangePasswordForm extends Component {
    constructor(...args) {
        super(...args);
        this.onPasswordChange=this.onPasswordChange.bind(this);
        this.onButtonPress=this.onButtonPress.bind(this);
        this.onBackPress=this.onBackPress.bind(this);
    }

    onPasswordChange(text) {
        this.props.passwordChanged(text);
    }

    onButtonPress() {
        const { apiKey,password } = this.props;
        this.props.changePassword({ apiKey, password});
    }

    onBackPress(){
        const { apiKey,navigation } = this.props;
        this.props.handleBack({ apiKey,navigation });
    }

    render() {
        const { password } = this.props;
        return (
            <KeyboardAwareScrollView style={styles.container}>
                <Appbar.Header styles={styles.bottom}>
                    <Appbar.BackAction onPress={() => {this.onBackPress()}} />
                </Appbar.Header>
                <Header>{header}</Header>
                <TextInput
                    label={enter_new_password}
                    value={password}
                    onChangeText={this.onPasswordChange}
                    secureTextEntry/>
                    

                <Button
                    mode='contained'
                    style={styles.button}
                    onPress={this.onButtonPress}>
                    {change_password}
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
    const { password, apiKey } = state.changePassword;
    return { password, apiKey  };
};

export default connect(mapStateToProps,{
    passwordChanged,
    changePassword,
    handleBack,
})(ChangePasswordForm);