import React, { Component } from "react";
import { View, StyleSheet } from "react-native";
import Button from "../common/Button";
import Header from "../common/Header";

import { TriviaManagementStrings } from '../../locale/locale_heb';

const {
    header,
    subjectManagement,
    questionManagement,
    viewQuestions,
} = TriviaManagementStrings;


const buttons = [
    {name: subjectManagement},
    {name: questionManagement},
    {name: viewQuestions}
]

class TriviaManagement extends Component {
    constructor(...args) {
        super(...args);
        this.renderButtons = this.renderButtons.bind(this);
    }

    renderButtons (){
        return (buttons.map(
            btn => (
                <Button
                    mode='contained'
                    style={styles.button}
                >
                    {btn.name}
                </Button>
            )
        )
        )
    }

    render(){
        return (
            <View style={styles.container}>
                <Header>{header}</Header>
                {this.renderButtons()}
            </View>
        )
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
});

export default TriviaManagement;