import React, {Component} from "react";
import {StyleSheet, View, Text, FlatList} from "react-native";
import Button from "../common/Button";
import Header from "../common/Header";
import { Checkbox } from 'react-native-paper';

const questions = [
    {
        id: "1",
        question: "מי הוא ראש הממשלה?",
        checked: "checked"
    },
    {
        id: "2",
        question: "בירת צרפת היא?",
        checked: "checked"
    },
    {
        id: "3",
        question: "כמה ימים יש בשבוע?",
        checked: "unchecked"
    },
    {
        id: "4",
        question: "כמה זה 10:(2+2)?",
        checked: "checked"
    },
]

class AddTriviaMission extends Component{
    constructor(...args) {
        super(...args);
        this.renderQuestions = this.renderQuestions.bind(this);
    }

    renderQuestions(){
        let renderItem = ({item}) => {
            return(
                <View>
                    <Checkbox.Item label={item.question} status={item.checked}/>
                </View>
            )
        }

        return (
            <FlatList
                data={questions}
                renderItem={renderItem}
                keyExtractor={(item) => item.id}
            />
        )
    }

    render(){
        return (
            <View style={styles.container}>
                <Header>בחר שאלות עבור המשימה</Header>
                {this.renderQuestions()}
                <View style={{flex: 0.5, flexDirection: "column", alignItems: "center"}}>
                    <Button
                        mode="contained"
                        style={styles.button}
                    >הוסף משימה
                    </Button>
                </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        width: '100%',
    },
    button: {
        margin: 30,
        width: "80%"
    },
})

export default AddTriviaMission;