import React, { Component } from "react";
import {View, Text, StyleSheet, FlatList, KeyboardAvoidingView} from "react-native";
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import Header from "../common/Header";
import Button from "../common/Button";
import TextInput from "../common/TextInput";
import DropDownPicker from 'react-native-dropdown-picker';
import { List } from 'react-native-paper';


const subjects = [
    "ספורט","אקטואליה", "פוליטיקה"
]


class AddTriviaQuestion extends Component{
    constructor(...args) {
        super(...args);
        this.renderDropDownSubject = this.renderDropDownSubject.bind(this);
        this.renderQuestionsInputs = this.renderQuestionsInputs.bind(this);
        this.renderQuestion = this.renderQuestion.bind(this);
    }

    renderQuestion(){
        return (
            <TextInput
                label={"הכנס שאלה"}
            />
        )
    }

    renderDropDownSubject(){
        return (
            <DropDownPicker
                items={subjects}
                containerStyle={{ height: 40}}
                style={{ backgroundColor: '#fafafa' }}
                itemStyle={{
                    justifyContent: 'flex-start',
                }}
                dropDownStyle={{ backgroundColor: '#fafafa', marginTop: 2 }}
                placeholder={"הכנס נושא טריוויה"}
            />
        )
    }

    renderQuestionsInputs(){
        const inputParams = [
            {name: "input", placeholder: "הכנס תשובה שגויה" ,key: "1"},
            {name: "input", placeholder: "הכנס תשובה שגויה" ,key: "2"},
            {name: "input", placeholder: "הכנס תשובה שגויה" ,key: "3"},
            {name: "input", placeholder: "הכנס תשובה נכונה" ,key: "4"},
            {
                name: "button",
                placeholder: "הוסף שאלה" ,
                key: "5",
            }
        ]

        return (
            <FlatList
                data={inputParams}
                keyExtractor={(item) => item.key}
                renderItem={({ item }) => (
                    item.name === "input" ?
                        (<TextInput
                        label={item.placeholder}
                        />) : (
                            <Button
                                mode='contained'
                                style={styles.button}
                            >{item.placeholder}</Button>
                        )
                )}
            />
        )
    }

    render(){
        return (
            <KeyboardAvoidingView style={styles.container}>
                <Header>הוספת שאלת טריוויה</Header>
                {this.renderQuestion()}
                {this.renderDropDownSubject()}
                {this.renderQuestionsInputs()}
            </KeyboardAvoidingView>
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

})

export default AddTriviaQuestion;