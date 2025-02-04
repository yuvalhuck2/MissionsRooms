import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator, Checkbox, List } from "react-native-paper";
import { connect } from "react-redux";
import {
  addTemplate,
  changeDeterministic,
  changeOpenQuestion,
  changeStory,
  changeTrivia,
  missionsChanged,
  searchMission,
} from "../../actions/AddRoomTemplateActions";
import {
  DETERMINISTIC_NAME,
  OPEN_QUESTION_NAME,
  STORY_NAME,
  TRIVIA_NAME,
} from "../../actions/types";
import { theme } from "../../core/theme";
import {
  AddStrings,
  ChooseMissionsTemplateStrings,
} from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";

const {
  header,
  deterministic_name,
  question,
  search,
  deterministic_label,
  story_label,
  open_question_label,
  trivia_label,
  choose_type,
  story_description,
  open_question_name,
  trivia_question_name,
  see_questions,
} = ChooseMissionsTemplateStrings;

const { addButton } = AddStrings;

class ChooseMissionsForTemplateForm extends Component {
  constructor(...args) {
    super(...args);
    this.onMissionsChanged = this.onMissionsChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.renderItem = this.renderItem.bind(this);
    this.getMissionPresentation = this.getMissionPresentation.bind(this);
    this.onSearchPress = this.onSearchPress.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  onMissionsChanged(mission, toDelete) {
    const { missionsToAdd } = this.props;
    list = [];
    if (toDelete) {
      list = missionsToAdd.filter((x) => x.missionId != mission.missionId);
    } else {
      list = [...missionsToAdd, mission];
    }
    this.props.missionsChanged(list);
  }

  onButtonPress() {
    const {
      name,
      minimalMissionsToPass,
      missionsToAdd,
      type,
      apiKey,
      navigation,
    } = this.props;
    this.props.addTemplate({
      name,
      minimalMissionsToPass,
      missionsToAdd,
      type,
      apiKey,
      navigation,
    });
  }

  onSearchPress() {
    const {
      notFilteredMission,
      deterministic,
      trivia,
      story,
      openQuestion,
    } = this.props;
    this.props.searchMission({
      notFilteredMission,
      deterministic,
      trivia,
      story,
      openQuestion,
    });
  }

  getMissionPresentation(mission) {
    switch (mission.name) {
      case DETERMINISTIC_NAME:
        return deterministic_name + "\n" + question + mission.question[0];
      case STORY_NAME:
        return story_description;
      case OPEN_QUESTION_NAME:
        return open_question_name + "\n" + question + mission.question[0];
      case TRIVIA_NAME:
        return trivia_question_name;
      default:
    }
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

  renderItem(mission, toCheck) {
    return [
      <Checkbox.Item
        key={mission.missionId}
        label={this.getMissionPresentation(mission)}
        status={toCheck ? "checked" : "unchecked"}
        onPress={() => this.onMissionsChanged(mission, toCheck)}
      />,
      mission.name == TRIVIA_NAME ? (
        <List.Accordion title={see_questions}>
          {this.presentQuestions(mission.question)}
        </List.Accordion>
      ) : null,
    ];
  }

  presentQuestions(questions) {
    console.log(questions);
    return questions.map((question) => <List.Item title={question} />);
  }

  renderCheckBoxItems() {
    const { presentedMissions, missionsToAdd } = this.props;
    let missionIds = missionsToAdd.map((x) => x.missionId);
    return presentedMissions
      .filter((x) => !missionIds.includes(x.missionId))
      .map((mission) => this.renderItem(mission, false));
  }

  renderChosenCheckBoxItems() {
    const { missionsToAdd } = this.props;
    return missionsToAdd.map((mission) => this.renderItem(mission, true));
  }

  renderIcon(toCheck) {
    return toCheck ? "check-circle-outline" : "checkbox-blank-circle-outline";
  }

  renderList() {
    const { deterministic, story, openQuestion, trivia } = this.props;
    return (
      <List.Accordion title={choose_type}>
        <List.Item
          title={deterministic_label}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(deterministic)} />
          )}
          onPress={this.props.changeDeterministic}
        />
        <List.Item
          title={trivia_label}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(trivia)} />
          )}
          onPress={this.props.changeTrivia}
        />
        <List.Item
          title={story_label}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(story)} />
          )}
          onPress={this.props.changeStory}
        />
        <List.Item
          title={open_question_label}
          left={(props) => (
            <List.Icon {...props} icon={this.renderIcon(openQuestion)} />
          )}
          onPress={this.props.changeOpenQuestion}
        />
      </List.Accordion>
    );
  }

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackAction} />
        <Header>{header}</Header>
        {this.renderList()}
        <Button
          mode="contained"
          style={styles.button}
          onPress={this.onSearchPress}
        >
          {search}
        </Button>
        {this.renderChosenCheckBoxItems()}
        {this.renderCheckBoxItems()}
        {this.renderButton()}
        {this.renderError()}
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
    deterministic,
    notFilteredMission,
    story,
    openQuestion,
    trivia,
    name,
    minimalMissionsToPass,
    missionsToAdd,
    type,
    presentedMissions,
    apiKey,
    loading,
    errorMessage,
  } = state.addRoomTemplate;
  return {
    deterministic,
    notFilteredMission,
    story,
    openQuestion,
    trivia,
    name,
    minimalMissionsToPass,
    missionsToAdd,
    type,
    presentedMissions,
    apiKey,
    loading,
    errorMessage,
  };
};

export default connect(mapStateToProps, {
  missionsChanged,
  addTemplate,
  changeDeterministic,
  changeTrivia,
  changeStory,
  changeOpenQuestion,
  searchMission,
})(ChooseMissionsForTemplateForm);
