import {Dimensions, StyleSheet, Text, View} from "react-native";
import {theme} from "../../core/theme";
import {connect} from 'react-redux';
import {suggestionChange, addSuggestion} from "../../actions";
import {AddSuggestionStrings} from "../../locale/locale_heb";
import {Component} from "react";
import Button from "../common/Button";
import React from "react";
import {KeyboardAwareScrollView} from "react-native-keyboard-aware-scroll-view";
import Header from "../common/Header";
import TextInput from "../common/TextInput";


const {
    header,
    addSuggestionText,
    send_suggestion,
} = AddSuggestionStrings;


class AddSuggestionForm extends Component {
    constructor(...args) {
        super(...args);
        this.onSuggestionChange=this.onSuggestionChange.bind(this);
        this.onButtonPress=this.onButtonPress.bind(this);
    }

    onSuggestionChange(text) {
        this.props.suggestionChange(text);
    }

    onButtonPress() {
        const { apiKey,suggestion, navigation } = this.props;
        this.props.addSuggestion({ apiKey,suggestion, navigation });
    }

    render() {
        const { apiKey,suggestion, navigation  } = this.props;
        return (
            <KeyboardAwareScrollView style={styles.container}>
                <Header>{header}</Header>
                <TextInput
                    label={addSuggestionText}
                    value={suggestion}
                    onChangeText={this.onSuggestionChange}
                    //placeholder='Enter suggestion'
                />

                <Button
                    mode='contained'
                    style={styles.button}
                    onPress={this.onButtonPress}
                >
                    {send_suggestion}
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
    const { suggestion,apiKey } = state.addSuggestion;
    return { suggestion,apiKey  };
};

export default connect(mapStateToProps,{
    suggestionChange,
    addSuggestion,
})(AddSuggestionForm);