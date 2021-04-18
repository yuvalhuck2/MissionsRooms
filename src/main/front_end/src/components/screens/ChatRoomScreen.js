import { sendMessage,loadMessages } from '../../actions/ChooseRoomTeacherActions.js';
import {GiftedChat} from 'react-native-gifted-chat';
import { StyleSheet, Text, View, FlatList,TextInput,KeyboardAvoidingView,TouchableOpacity,Button } from 'react-native';
import React from "react";
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import { Component } from 'react';
import { useState, useCallback, useEffect } from 'react'

class ChatRoomForm extends Component {

    constructor(...args) {
        super(...args);
        this.state = {
            messages: [],
            messageCount:0,
        };

    }

    componentWillMount() {



    }

    componentDidUpdate(prevProps) {

        const {messagesProps}= this.props;


        if(prevProps.messagesProps.length!==messagesProps.length) {
            //alert(messagesProps.length+" "+this.state.messages.length)

            let messages = messagesProps;
            let newMessagesLength = messages.length

            let newMessages = messages.slice(this.state.messageCount)




            this.setState((prevState) => ({
                messages: GiftedChat.append(prevState.messages, newMessages),
                messageCount: newMessagesLength,
            }));
        }
    }

    onSend(messages = []) {
        const {name,roomId,navigation,apiKey} = this.props;
        this.setState((previousState) => ({
            messages: GiftedChat.append(previousState.messages, messages),
        }));

        this.props.sendMessage({navigation,newMessage:messages[0],roomId,apiKey})
    }




    render() {
        const {name,roomId,navigation,apiKey,messages} = this.props;

        const user = {
            _id: name,
            name,
        };



        return (
            <View style={{flex: 1}}>
                <GiftedChat
                    messages={this.state.messages}
                    onSend={(newMessages) => this.onSend(newMessages)}
                        //this.props.sendMessage({navigation,newMessage:newMessages[0],roomId,apiKey})}
                    user={user}
                    renderUsernameOnMessage
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    textInput: {
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        width: '50%'
    }
});






const mapStateToProps = (state) => {
    const { roomId, errorMessage , apiKey,name,messagesProps,typing} = state.ChatRoom;
    return { roomId, errorMessage , apiKey,name,messagesProps,typing};
};

export default connect(mapStateToProps, {
    sendMessage,
})(ChatRoomForm);






