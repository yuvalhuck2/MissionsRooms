import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { ActivityIndicator, Checkbox, List } from "react-native-paper";
import { connect } from "react-redux";
import {
  addUser,
  changeAlias,
  changeClassNumber,
  changeFirstName,
  changeGrade,
  changeGroup,
  changeLastName,
  changeSupervisor,
} from "../../actions/AddUserActions";
import { theme } from "../../core/theme";
import { AddStrings, AddUserStrings, Grades } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
  student_header,
  teacher_header,
  enter_class_number,
  choose_grade,
  enter_first_name,
  enter_alias,
  enter_last_name,
  choose_group,
  superviosr_label,
} = AddUserStrings;

const { addButton } = AddStrings;

const { yud, yudAlef, yudBeit } = Grades;

const gradeMap = new Map([
  [yud, "0"],
  [yudAlef, "1"],
  [yudBeit, "2"],
]);

class AddUserForm extends Component {
  constructor(...args) {
    super(...args);
    this.OnChangeGrade = this.onChangeGrade.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onClassNumberChanged = this.onClassNumberChanged.bind(this);
    this.onChangeGroup = this.onChangeGroup.bind(this);
    this.onAliasChanged = this.onAliasChanged.bind(this);
    this.onFirstNameChanged = this.onFirstNameChanged.bind(this);
    this.onLastNameChanged = this.onLastNameChanged.bind(this);
    this.onSupervisorChanged = this.onSupervisorChanged.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  onClassNumberChanged(number) {
    return this.props.changeClassNumber(number);
  }

  onChangeGrade(grade) {
    return this.props.changeGrade(gradeMap.get(grade));
  }

  onChangeGroup(group) {
    return this.props.changeGroup(group);
  }

  onAliasChanged(alias) {
    return this.props.changeAlias(alias);
  }

  onFirstNameChanged(name) {
    return this.props.changeFirstName(name);
  }

  onLastNameChanged(name) {
    return this.props.changeLastName(name);
  }

  onSupervisorChanged() {
    return this.props.changeSupervisor();
  }

  onButtonPress() {
    const {
      alias,
      firstName,
      lastName,
      grade,
      classNumber,
      classGroup,
      isStudent,
      isSupervisor,
      apiKey,
    } = this.props;
    this.props.addUser({
      alias,
      firstName,
      lastName,
      grade,
      classNumber,
      classGroup,
      isStudent,
      isSupervisor,
      apiKey,
    });
  }

  renderSpinner() {
    return (
      <ActivityIndicator
        animating={true}
        color={theme.colors.primary}
        size="large"
      />
    );
  }

  renderButton() {
    const { loading } = this.props;

    return loading ? (
      this.renderSpinner()
    ) : (
      <Button
        mode="contained"
        style={styles.button}
        onPress={this.onButtonPress}
      >
        {addButton}
      </Button>
    );
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

  renderIcon(toCheck) {
    return toCheck ? "check-circle-outline" : "checkbox-blank-circle-outline";
  }

  renderIconForGrades(toCheck) {
    const { grade } = this.props;
    return this.renderIcon(grade == gradeMap.get(toCheck));
  }

  renderGradesList() {
    return (
      <List.Accordion key={choose_grade} title={choose_grade}>
        <List.Item
          title={yud}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIconForGrades(yud)} />
          )}
          onPress={() => this.onChangeGrade(yud)}
        />
        <List.Item
          title={yudAlef}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIconForGrades(yudAlef)} />
          )}
          onPress={() => this.onChangeGrade(yudAlef)}
        />
        <List.Item
          title={yudBeit}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIconForGrades(yudBeit)} />
          )}
          onPress={() => this.onChangeGrade(yudBeit)}
        />
      </List.Accordion>
    );
  }

  renderGroupsList() {
    const { classGroup } = this.props;
    return (
      <List.Accordion key={choose_group} title={choose_group}>
        <List.Item
          title={"A"}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(classGroup == "A")} />
          )}
          onPress={() => this.onChangeGroup("A")}
        />
        <List.Item
          title={"B"}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(classGroup == "B")} />
          )}
          onPress={() => this.onChangeGroup("B")}
        />
      </List.Accordion>
    );
  }

  renderClassNumberTextBox() {
    const { classNumber } = this.props;
    return (
      <TextInput
        key={enter_class_number}
        label={enter_class_number}
        keyboardType="numeric"
        onChangeText={this.onClassNumberChanged}
        value={classNumber}
        placeholder={enter_class_number}
      />
    );
  }

  renderCheckSupervisorBox() {
    const { isSupervisor } = this.props;
    return (
      <Checkbox.Item
        label={superviosr_label}
        status={isSupervisor ? "checked" : "unchecked"}
        onPress={this.onSupervisorChanged}
      />
    );
  }

  renderStudentPart() {
    const { isStudent } = this.props;
    return isStudent
      ? [
          this.renderGradesList(),
          this.renderGroupsList(),
          this.renderClassNumberTextBox(),
        ]
      : this.renderCheckSupervisorBox();
  }

  renderAliasTextBox() {
    const { alias } = this.props;
    return (
      <TextInput
        label={enter_alias}
        onChangeText={this.onAliasChanged}
        value={alias}
        placeholder={enter_alias}
      />
    );
  }

  renderFirstNameTextBox() {
    const { firstName } = this.props;
    return (
      <TextInput
        label={enter_first_name}
        onChangeText={this.onFirstNameChanged}
        value={firstName}
        placeholder={enter_first_name}
      />
    );
  }

  renderLastNameTextBox() {
    const { lastName } = this.props;
    return (
      <TextInput
        label={enter_last_name}
        onChangeText={this.onLastNameChanged}
        value={lastName}
        placeholder={enter_last_name}
      />
    );
  }

  renderHeader() {
    const { isStudent } = this.props;
    return isStudent ? student_header : teacher_header;
  }

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAwareScrollView>
          <CustomAppbar backAction={this.onBackAction} />
          <Header>{this.renderHeader()}</Header>
          {this.renderStudentPart()}
          {this.renderAliasTextBox()}
          {this.renderFirstNameTextBox()}
          {this.renderLastNameTextBox()}
          {this.renderButton()}
          {this.renderError()}
        </KeyboardAwareScrollView>
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
  },
  button: {
    marginTop: 24,
  },
  row: {
    flexDirection: "row",
    marginTop: 10,
  },
  link: {
    fontWeight: "bold",
    color: theme.colors.primary,
  },
  errorTextStyle: {
    fontSize: 25,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const {
    alias,
    firstName,
    lastName,
    grade,
    classNumber,
    classGroup,
    isStudent,
    isSupervisor,
    apiKey,
    loading,
    errorMessage,
  } = state.addUser;
  return {
    alias,
    firstName,
    lastName,
    grade,
    classNumber,
    classGroup,
    isStudent,
    isSupervisor,
    apiKey,
    loading,
    errorMessage,
  };
};

export default connect(mapStateToProps, {
  changeClassNumber,
  changeGrade,
  changeGroup,
  changeAlias,
  changeFirstName,
  changeLastName,
  changeSupervisor,
  addUser,
})(AddUserForm);
