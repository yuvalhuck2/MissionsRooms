import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator,Appbar, List, Dialog, Portal, Searchbar } from 'react-native-paper';
import { connect } from 'react-redux';
import {   searchChanged, messageChanged, deleteMessage, handleBack, filterMessages} from '../../actions/WatchMessagesActions';
import { theme } from '../../core/theme';
import { WatchMessagesStrings } from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  enter_search,
  delete_message,
  date,
  time,
  watch_messages,
  exit,
} = WatchMessagesStrings;

class WatchMessagesForm extends Component {
  constructor(...args) {
    super(...args);
    this.onSearchChanged = this.onSearchChanged.bind(this);
    this.onFilterMessages=this.onFilterMessages.bind(this);
    this.onMessageChanged=this.onMessageChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.getTimeString = this.getTimeString.bind(this);
  }

  onSearchChanged(text) {
    this.props.searchChanged(text);
  }

  onFilterMessages() {
    const {search,allMessages} = this.props;
    this.props.filterMessages(search, allMessages);
  }

  onMessageChanged(message) {
    this.props.messageChanged(message);
  }

  onButtonPress() {
    const {apiKey, message} = this.props;
    this.props.deleteMessage({apiKey, message});
  }

  onBackPress(){
    const {navigation,apiKey} =this.props;
    this.props.handleBack({navigation,apiKey});
  }

  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size='large'
        styles={styles.button}
      />
    );
  }

  renderTextBox(){
    const {search} = this.props;
      return (
      <Searchbar
          label={enter_search}
          value={search}
          onChangeText={this.onSearchChanged}
          icon = {"magnify"}
          onIconPress={this.onFilterMessages}
        />
      );
  }

  renderListItems(){
    const {presentedMessages} = this.props;
    return(
        presentedMessages.map((message)=>
        <List.Item
        title={message.writer}
        description={this.getTimeString(message)}
        left={props => <List.Icon {...props} icon="account" />}
        key={message.id}
        onPress={()=>this.onMessageChanged(message)}
        />
        )
    )
  }

  getTimeString(message) {
      return date + message.date + " " + time + message.time;
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
      <Dialog.Actions>
        <Button
          styles={styles.button}
          onPress={this.onButtonPress}>
          {delete_message}
        </Button>
      </Dialog.Actions>)
  
  }

  render() {
    const { message} = this.props;
    console.log(Object.keys(message))
    return (
        <KeyboardAwareScrollView style={styles.container}>
         <Appbar.Header styles={styles.bottom}>
           <Appbar.BackAction onPress={() => {this.onBackPress()}} />
         </Appbar.Header>
        <Header>{watch_messages}</Header>
        {this.renderTextBox()}
        {this.renderListItems()}
        <Portal>
            <Dialog visible={Object.keys(message).length!==0} onDismiss={()=>this.onMessageChanged({})}>
            <Dialog.Title>{message.writer}</Dialog.Title>
            <Dialog.Content>
                <Text>
                    {this.getTimeString(message) +"\n" + message.message}
                </Text>
                </Dialog.Content>
            {this.renderButton()}
            <Dialog.Actions>
                <Button styles={styles.button} onPress={()=>this.onMessageChanged({})}>{exit}</Button>
            </Dialog.Actions>
            </Dialog>
        </Portal>
        {this.renderError()}
        </KeyboardAwareScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 0,
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
  },
  button: {
    marginTop: 0,
    width:100,
  },
  row: {
    flexDirection: 'row',
    marginTop: 0,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: 'center',
    color: theme.colors.error,
  },
  bottom: {
    position: 'absolute',
    left: 0,
    right: 0,
    bottom: 0,
  },
});

const mapStateToProps = (state) => {
  const { search, message, allMessages, presentedMessages, apiKey, errorMessage, loading } = state.WatchMessages;
  return { search, message, allMessages, presentedMessages, apiKey, errorMessage, loading };
};

export default connect(mapStateToProps, {
  searchChanged,
  messageChanged,
  deleteMessage,
  handleBack,
  filterMessages,
})(WatchMessagesForm);
