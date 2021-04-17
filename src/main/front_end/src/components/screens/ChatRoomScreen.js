import { sendMessage,loadMessages } from '../../actions/ChooseRoomTeacherActions.js';
import {GiftedChat} from 'react-native-gifted-chat';
import { StyleSheet, Text, View, FlatList,TextInput,KeyboardAvoidingView,TouchableOpacity,Button } from 'react-native';
import React from "react";
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import { Component } from 'react';
import { useState, useCallback, useEffect } from 'react'

class ChatRoomForm extends Component {
    state = {
        messages: [],
        messageCount:1,
    };
    constructor(...args) {
        super(...args);

    }

    componentWillMount() {
        this.setState({
            messages: [
                {
                    _id: 1,
                    text: 'Hello developer',
                    createdAt: new Date(),
                    user: {
                        _id: 2,
                        name: 'React Native',
                        avatar: 'https://facebook.github.io/react/img/logo_og.png',
                    },
                },
            ],
        });
    }

    UNSAFE_componentWillReceiveProps(a,b){
        const {messagesProps}= this.props;
        let messages = messagesProps;
        let newMessagesLength = messages.length

        let newMessages = messages.slice(this.state.messageCount)

        this.setState((prevState)=>({
            messages:GiftedChat.append(prevState.messages,newMessages),
            messageCount:newMessagesLength,
        }));
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
    //return(
    /*
    <GiftedChat
        messages={messages}
        onSend={(message)=>this.props.sendMessage(message)}
        user={{name:name}}
        />*/
    /*
    <View style={styles.container}>
        <FlatList
                  data={messages}
                 renderItem={this.renderItem}
                  inverted
        />
        <KeyboardAvoidingView behavior="padding">
        <View style={styles.footer}>
            <TextInput
                value={typing}
                onChangeText={text => this.setState({typing:text})}
                style={styles.input}
                underlineColorAndroid="transparent"
                placeholder="Type something nice"
            />
            <TouchableOpacity onPress={this.sendMessage.bind(this)}>
                <Text style={styles.send}>Send</Text>
            </TouchableOpacity>
        </View>
        </KeyboardAvoidingView>


    </View>*/
    //    );
//};

/*
    renderItem({item}) {
        return (
            <View style={styles.row}>
                <Text style={styles.sender}>{item.sender}</Text>
                <Text style={styles.message}>{item.message}</Text>
            </View>
        );
    }*/
/*
    componentDidMount() {
        const {roomId,message,messages}=this.props;
        this.props.loadMessages();
        GiftedChat.append(messages,message);

    }
    componentWillUnmount() {
        this.props.closeChat();
    }*/

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






