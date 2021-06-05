import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import {
  ActivityIndicator,
  Dialog,
  List,
  Portal,
  Searchbar,
} from "react-native-paper";
import { connect } from "react-redux";
import { IT, Student, Supervisor, Teacher } from "../../actions/OpCodeTypes";
import {
  changeDialog,
  filterUsers,
  handleBack,
  messageChanged,
  searchChanged,
  sendMessage,
} from "../../actions/WatchProfileActions";
import { theme } from "../../core/theme";
import { RolesStrings, WatchProfileStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
  search_profile,
  enter_search,
  press_profile,
  name,
  role,
  points,
  type_message,
  send_message,
  exit,
} = WatchProfileStrings;

const { student, teacher, it, supervisor } = RolesStrings;

class WatchProfileForm extends Component {
  constructor(...args) {
    super(...args);
    this.onSearchChanged = this.onSearchChanged.bind(this);
    this.onFilterUsers = this.onFilterUsers.bind(this);
    this.onMessageChanged = this.onMessageChanged.bind(this);
    this.onChangeDialog = this.onChangeDialog.bind(this);
    this.onDismissDialog = this.onDismissDialog.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
  }

  onSearchChanged(text) {
    this.props.searchChanged(text);
  }

  onFilterUsers() {
    const { search, allUsers } = this.props;
    this.props.filterUsers(search, allUsers);
  }

  onMessageChanged(text) {
    this.props.messageChanged(text);
  }

  onButtonPress() {
    const { apiKey, message, profile } = this.props;
    this.props.sendMessage({ apiKey, message, profile });
  }

  onBackPress() {
    const { navigation, apiKey } = this.props;
    this.props.handleBack({ navigation, apiKey });
  }

  onDismissDialog() {
    this.onChangeDialog("");
    this.onMessageChanged("");
  }

  onChangeDialog(alias) {
    this.props.changeDialog(alias);
  }

  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size="large"
        styles={styles.button}
      />
    );
  }

  renderTextBox() {
    const { search } = this.props;
    return (
      <Searchbar
        label={enter_search}
        value={search}
        onChangeText={this.onSearchChanged}
        icon={"magnify"}
        onIconPress={this.onFilterUsers}
      />
    );
  }

  renderListItems() {
    const { presentedUsers } = this.props;
    return presentedUsers.map((user) => (
      <List.Item
        title={user.alias}
        description={this.renderUserDetails(user)}
        left={(props) => <List.Icon {...props} icon="account" />}
        key={user.alias}
        onPress={() => this.onChangeDialog(user.alias)}
      />
    ));
  }

  getHebrewType(userType) {
    switch (userType) {
      case Student:
        return student;
      case Teacher:
        return teacher;
      case IT:
        return it;
      case Supervisor:
        return supervisor;
    }
  }

  renderUserDetails(user) {
    let nameDef =
      user.firstName != null
        ? name + user.firstName + " " + user.lastName + " "
        : "";
    let details = nameDef + role + this.getHebrewType(user.userType) + " ";
    return user.points != null ? details + points + user.points : details;
  }

  renderError() {
    const { errorMessage } = this.props;
    if (errorMessage && errorMessage !== "") {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{errorMessage}</Text>
        </View>
      );
    }
  }

  renderButton() {
    const { loading } = this.props;

    return loading ? (
      this.renderSpinner()
    ) : (
      <Dialog.Actions>
        <Button styles={styles.button} onPress={this.onButtonPress}>
          {send_message}
        </Button>
      </Dialog.Actions>
    );
  }

  render() {
    const { profile, message } = this.props;
    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAwareScrollView>
          <CustomAppbar backAction={this.onBackPress} />
          <Header>{search_profile}</Header>
          <Text>{press_profile}</Text>
          {this.renderTextBox()}
          {this.renderListItems()}
          <Portal>
            <Dialog visible={profile !== ""} onDismiss={this.onDismissDialog}>
              <Dialog.Title>{profile}</Dialog.Title>
              <Dialog.Content>
                <TextInput
                  label={type_message}
                  value={message}
                  onChangeText={this.onMessageChanged}
                  multiline={true}
                />
              </Dialog.Content>
              {this.renderButton()}
              <Dialog.Actions>
                <Button styles={styles.button} onPress={this.onDismissDialog}>
                  {exit}
                </Button>
              </Dialog.Actions>
            </Dialog>
          </Portal>
          {this.renderError()}
        </KeyboardAwareScrollView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 0,
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 0,
    width: 100,
  },
  row: {
    flexDirection: "row",
    marginTop: 0,
  },
  link: {
    fontWeight: "bold",
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
  bottom: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
  },
});

const mapStateToProps = (state) => {
  const {
    search,
    message,
    profile,
    allUsers,
    presentedUsers,
    apiKey,
    errorMessage,
    loading,
  } = state.WatchProfile;
  return {
    search,
    message,
    profile,
    allUsers,
    presentedUsers,
    apiKey,
    errorMessage,
    loading,
  };
};

export default connect(mapStateToProps, {
  searchChanged,
  messageChanged,
  changeDialog,
  sendMessage,
  handleBack,
  filterUsers,
})(WatchProfileForm);
