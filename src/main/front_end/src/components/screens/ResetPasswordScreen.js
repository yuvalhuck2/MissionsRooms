import {Dimensions, StyleSheet, Text, View} from "react-native";
import {theme} from "../../core/theme";
import {connect} from 'react-redux';
import { changeAlias,resetPassword } from "../../actions/ResetPasswordActions";
import {ResetPasswordStrings} from "../../locale/locale_heb";
import { Appbar, ActivityIndicator } from 'react-native-paper';
import {Component} from "react";
import Button from "../common/Button";
import React from "react";
import {KeyboardAwareScrollView} from "react-native-keyboard-aware-scroll-view";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
    header,
    reset,
    enter_alias,
} = ResetPasswordStrings;


class ResetPasswordForm extends Component {
    constructor(...args) {
        super(...args);
        this.onAliasChanged=this.onAliasChanged.bind(this);
        this.onButtonPress=this.onButtonPress.bind(this);
    }

    onAliasChanged(text) {
        this.props.changeAlias(text);
    }

    onButtonPress() {
        const { alias, navigation } = this.props;
        this.props.resetPassword({alias, navigation });
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
    
      renderButton(){
        const {loading} = this.props
    
        return loading ? (
          this.renderSpinner()
        ) : (
            <Button
                mode='contained'
                style={styles.button}
                onPress={this.onButtonPress}>
                {reset}
            </Button>
        )
      }

    render() {
        const { alias, navigation } = this.props;
        return (
            <KeyboardAwareScrollView style={styles.container}>
                <Appbar.Header styles={styles.bottom}>
                    <Appbar.BackAction onPress={() => {navigation.goBack()}} />
                </Appbar.Header>
                <Header>{header}</Header>
                <TextInput
                    label={enter_alias}
                    value={alias}
                    onChangeText={this.onAliasChanged}
                    placeholder= {enter_alias}
                />
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
    const { alias, loading, errorMessage} = state.resetPassword;
    return { alias, loading, errorMessage};
};

export default connect(mapStateToProps,{
    changeAlias,
    resetPassword,
})(ResetPasswordForm);