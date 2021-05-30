import React, { Component } from 'react';
import { StyleSheet, Text, View,Alert } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator,Appbar, List, Dialog, Portal, Searchbar, DataTable, Switch} from 'react-native-paper';
import { connect } from 'react-redux';
import { Student, Supervisor, Teacher, IT } from '../../actions/OpCodeTypes';
import { searchChanged, editUserDetails, firstNameChanged, lastNameChanged, changeDialog,
  deleteUser, passToTransferGroup, handleBack, filterUsers,  changeEnableFirstName,
  changeEnableLastName,passToTransferClassroom} from '../../actions/ManageUsersActions';
import { theme } from '../../core/theme';
import { WatchProfileStrings,RolesStrings, ManageUsersStrings ,DeleteUserString} from '../../locale/locale_heb';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
  enter_search,
  name,
  role,
  exit,
} = WatchProfileStrings;

const {
  student,
  teacher,
  supervisor,
} = RolesStrings;

const {
  sure,
    yes,
    no,
}=DeleteUserString;
const {
  edit_details,
  delete_user,
  enter_last_name,
  enter_first_name,
  enable_edit_first_name,
  enable_edit_last_name,
  manage_users,
  press_user_for_details,
  transfer,
} = ManageUsersStrings;

class ManageUsersForm extends Component {
  constructor(...args) {
    super(...args);
    this.onSearchChanged = this.onSearchChanged.bind(this);
    this.onFilterUsers=this.onFilterUsers.bind(this);
    this.onFirstNameChanged=this.onFirstNameChanged.bind(this);
    this.onChangeDialog=this.onChangeDialog.bind(this);
    this.onDismissDialog=this.onDismissDialog.bind(this);
    this.onEditButtonPress = this.onEditButtonPress.bind(this);
    this.onDeleteButtonPress = this.onDeleteButtonPress.bind(this);
    this.onBackPress = this.onBackPress.bind(this);
    this.passToTransferGroup=this.passToTransferGroup.bind(this);
    this.onLastNameChanged=this.onLastNameChanged.bind(this);
    this.renderDialogContent = this.renderDialogContent.bind(this)
    this.setEnableFirstName=this.setEnableFirstName.bind(this);
    this.setEnableLastName=this.setEnableLastName.bind(this);
    this.passToTransferClassroom=this.passToTransferClassroom.bind(this);
  }

  onSearchChanged(text) {
    this.props.searchChanged(text);
  }

  onFilterUsers() {
    const {search,allUsers} = this.props;
    this.props.filterUsers(search, allUsers);
  }

  onFirstNameChanged(text) {
    this.props.firstNameChanged(text);
  }

  onLastNameChanged(text) {
    this.props.lastNameChanged(text);
  }

  passToTransferGroup(){
      const {apiKey,navigation,profile} = this.props;
      this.props.passToTransferGroup({apiKey,navigation,alias:profile});
      this.onLastNameChanged('');
      this.onFirstNameChanged('');

  }

  passToTransferClassroom(){
    const {apiKey,navigation} = this.props;
    //this.onDismissDialog();
    this.props.passToTransferClassroom({apiKey,navigation});
    this.onLastNameChanged('');
    this.onFirstNameChanged('');
  }

  onEditButtonPress(){
    const {apiKey,firstName,lastName,profile} = this.props;
    return this.props.editUserDetails({apiKey,firstName,lastName,alias:profile});
  }

  onDeleteButtonPress(){
    const {apiKey,profile} = this.props;

    return Alert.alert(sure,"",[
        {text:yes,onPress:()=>this.props.deleteUser({apiKey,alias:profile})},
        {text:no,onPress:()=>{}}
    ]);

  }

  setEnableFirstName(){
    const {enableFirstName} = this.props;
    this.props.changeEnableFirstName(!enableFirstName)
  }

  setEnableLastName(){
    const {enableLastName} = this.props;
    this.props.changeEnableLastName(!enableLastName)
  }

  onBackPress(){
    const {navigation,apiKey} =this.props;
    this.props.handleBack({navigation,apiKey});
  }

  onDismissDialog(){
    this.onLastNameChanged('')
    this.onFirstNameChanged('')
    this.onChangeDialog('')
  }

  onChangeDialog(alias){
    const{profile, presentedUsers}=this.props;
    this.props.changeDialog(alias);
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
          onIconPress={this.onFilterUsers}
        />
      );
  }

  renderListItems(){
    const {presentedUsers} = this.props;
    return(
        presentedUsers.map((user)=>
        <List.Item
        title={user.alias}
        description={this.renderUserDetails(user)}
        left={props => <List.Icon {...props} icon="account" />}
        key={user.alias}
        onPress={()=>this.onChangeDialog(user.alias)}
        />
        )
    )

  }

  getHebrewType(userType){
    switch(userType){
      case Student:
        return student
      case Teacher:
        return teacher
      case Supervisor:
        return supervisor
    }
  }

  renderUserDetails(user){
    let nameDef = (user.firstName != null) ? name + user.firstName + " " + user.lastName + " " : "";
    let details = nameDef + role + this.getHebrewType(user.userType) + " ";
    return details;
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

  renderButtons(){
    const {loading,currUser} = this.props

    return loading ? (
      this.renderSpinner()
    ) : (this.createDialogActionsList());
  }

  createDialogActionsList() {
      const {currUser, profile} = this.props;
      if (currUser !== undefined) {
          let lst = [<Dialog.Actions key="edit">
              <Button
                  // styles={styles.button}
                  onPress={this.onEditButtonPress}>
                  {edit_details}
              </Button>
          </Dialog.Actions>,
              <Dialog.Actions key="delete">
                  <Button
                      styles={styles.button}
                      onPress={this.onDeleteButtonPress}>
                      {delete_user}
                  </Button>
              </Dialog.Actions>];
          if (currUser.userType === 'Student') {
              lst.push(<Dialog.Actions key="moveToAnotherGroup">
                  <Button
                      styles={styles.button}
                      onPress={this.passToTransferGroup}>
                      {transfer}
                  </Button>
              </Dialog.Actions>);
          }
          if (currUser.userType === 'Teacher' && !currUser.hasClassroom) {
              lst.push(<Dialog.Actions key="moveToAnotherClass">
                  <Button
                      styles={styles.button}
                      onPress={this.passToTransferClassroom}>
                      {transfer}
                  </Button>
              </Dialog.Actions>);
          }
          lst.push(<Dialog.Actions>
              <Button
                  styles={styles.button}
                  onPress={this.onDismissDialog}>
                  {exit}
              </Button>
          </Dialog.Actions>);
          return lst;
      }
  }



  renderDialogContent(){
    const {enableFirstName, enableLastName, firstName, lastName} = this.props;
     <Dialog.Content>
        <DataTable.Row>
        <DataTable.Cell>{enable_edit_first_name}</DataTable.Cell>
          <Switch  />
        </DataTable.Row>
        <DataTable.Row>
        <DataTable.Cell>{enable_edit_last_name}</DataTable.Cell>
          <Switch  />
        </DataTable.Row>
        {enableFirstName ? (
             <TextInput 
             label={enter_first_name}
             value={firstName}
             onChangeText={this.onFirstNameChanged}
             />
        ): null}
       {enableLastName ? (
             <TextInput 
             label={enter_last_name}
             value={lastName}
             onChangeText={this.onLastNameChanged}
             />
        ): null}
    </Dialog.Content>
  }

   renderPortal(){
    const {profile, enableFirstName, enableLastName, firstName, lastName,currUser,visible} = this.props;

       return <Portal>
              <Dialog visible={profile!==''&&visible} onDismiss={this.onDismissDialog}>
                  <Dialog.Title>{profile}</Dialog.Title>

                  <Dialog.Content>
                      <DataTable.Row>
                          <DataTable.Cell>{enable_edit_first_name}</DataTable.Cell>
                          <Switch value={enableFirstName} onValueChange={this.setEnableFirstName}/>
                      </DataTable.Row>
                      <DataTable.Row>
                          <DataTable.Cell>{enable_edit_last_name}</DataTable.Cell>
                          <Switch  value={enableLastName} onValueChange={this.setEnableLastName}/>
                      </DataTable.Row>
                      {enableFirstName ? (
                          <TextInput
                              label={enter_first_name}
                              value={firstName}
                              onChangeText={this.onFirstNameChanged}
                          />
                      ): null}
                      {enableLastName ? (
                          <TextInput
                              label={enter_last_name}
                              value={lastName}
                              onChangeText={this.onLastNameChanged}
                          />
                      ): null}
                  </Dialog.Content>
                  {this.renderDialogContent()}
                  {/* <Dialog.Content>
              {choose_action}
            </Dialog.Content> */}
                  {this.renderButtons()}
              </Dialog>
          </Portal>;

  }

  render() {
    const {profile, enableFirstName, enableLastName, firstName, lastName,currUser,loading} = this.props;
    return (
        <KeyboardAwareScrollView style={styles.container}>
         <Appbar.Header styles={styles.bottom}>
           <Appbar.BackAction onPress={() => {this.onBackPress()}} />
         </Appbar.Header>
        <Header>{manage_users}</Header>
        <Text>{press_user_for_details}</Text>
        {this.renderTextBox()}
        {this.renderListItems()}
        {loading?this.renderSpinner():this.renderPortal()}
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
  const { search, enableFirstName,firstName, lastName,enableLastName,
    classroom, group, profile, allUsers, presentedUsers, apiKey, errorMessage, loading,currUser,allClassrooms,visible } = state.manageUsers;
  return { search, enableFirstName, firstName, lastName, enableLastName,
    classroom, group, profile, allUsers, presentedUsers, apiKey, errorMessage, loading,currUser,allClassrooms,visible };
};

export default connect(mapStateToProps, {
  searchChanged,
  editUserDetails,
  firstNameChanged,
  lastNameChanged,
  changeDialog,
  deleteUser,
  passToTransferGroup,
  handleBack,
  filterUsers,
  changeEnableFirstName,
  changeEnableLastName,
    passToTransferClassroom,
})(ManageUsersForm);
