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

    }

    render() {
        const {name, messages,roomId,navigation} = this.props;

        const user = {
            _id: name,
            name,
        };

        return (
            <View style={{flex: 1}}>
                <GiftedChat
                    messages={messages}
                    onSend={(newMessages) => this.props.sendMessage({navigation,newMessage:newMessages[0],roomId,apiKey})}
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
    const { roomId, errorMessage , apiKey,name,messages,typing} = state.ChatRoom;
    return { roomId, errorMessage , apiKey,name,messages,typing};
};

export default connect(mapStateToProps, {
    sendMessage,
})(ChatRoomForm);






