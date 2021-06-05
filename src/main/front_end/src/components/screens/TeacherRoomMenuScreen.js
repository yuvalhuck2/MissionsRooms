import React, { Component } from "react";
import { Alert, SafeAreaView, StyleSheet, Text, View } from "react-native";
import { Card, Menu, Paragraph, ProgressBar, Title } from "react-native-paper";
import { connect } from "react-redux";
import {
  approveAnswer,
  closeRoom,
  enterChatTeacher,
} from "../../actions/ChooseRoomTeacherActions";
import {
  DETERMINISTIC_NAME,
  OPEN_QUESTION_NAME,
  STORY_NAME,
  TRIVIA_NAME,
} from "../../actions/types";
import { theme } from "../../core/theme";
import {
  ChooseMissionsTemplateStrings,
  TeacherRoomMenuStrings,
} from "../../locale/locale_heb";
import CustomAppbar from "../common/CustomAppbar";

const {
  chat,
  approve_answer,
  close_room,
  mission,
  of,
  yes,
  no,
  sure,
} = TeacherRoomMenuStrings;
const {
  deterministic_name,
  question,
  open_question_name,
  story_description,
  trivia_question_name,
} = ChooseMissionsTemplateStrings;

class TeacherRoomMenuForm extends Component {
  constructor(...args) {
    super(...args);
    this.onBackAction = this.onBackAction.bind(this);
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

  getMissionPresentation(mission) {
    if (mission != null) {
      switch (mission.name) {
        case DETERMINISTIC_NAME:
          return deterministic_name + "\n\t\t" + question + mission.question[0];
        case STORY_NAME:
          return story_description;
        case OPEN_QUESTION_NAME:
          return open_question_name + "\n" + question + mission.question[0];
        case TRIVIA_NAME:
          return trivia_question_name;
        default:
          return "";
      }
    }
  }

  getProgressPresentation(room) {
    return (
      mission +
      " " +
      room.currentMissionNumber +
      " " +
      of +
      " " +
      room.numberOfMissions
    );
  }

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    const { navigation, apiKey, roomsType, currentRoom } = this.props;
    console.log(currentRoom);
    //if(currentRoom!=''){
    let progress =
      currentRoom.currentMissionNumber / currentRoom.numberOfMissions;
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackAction} />
        <Card>
          <Card.Content>
            <Title>{currentRoom.name}</Title>
            <Paragraph>{this.getProgressPresentation(currentRoom)}</Paragraph>
            <ProgressBar progress={progress} color={theme.colors.primary} />
            <Paragraph>
              {this.getMissionPresentation(currentRoom.currentMission)}
            </Paragraph>
          </Card.Content>
        </Card>
        <View style={{ flex: 1 }}>
          <Menu.Item
            icon="chat"
            onPress={() =>
              this.props.enterChatTeacher({
                navigation,
                apiKey,
                roomId: currentRoom.roomId,
              })
            }
            title={chat}
          />
          <Menu.Item
            icon="answer"
            onPress={() =>
              this.props.approveAnswer({
                navigation,
                apiKey,
                roomId: currentRoom.roomId,
                roomName: currentRoom.name,
              })
            }
            title={approve_answer}
          />
          <Menu.Item
            icon="close"
            onPress={() =>
              Alert.alert(sure, "", [
                {
                  text: yes,
                  onPress: () =>
                    this.props.closeRoom({ navigation, apiKey, currentRoom }),
                },
                { text: no, onPress: () => {} },
              ])
            }
            title={close_room}
          />
        </View>
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 24,
    backgroundColor: theme.colors.primary,
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
    type,
    roomsType,
    currentRoom,
    apiKey,
    errorMessage,
  } = state.ChooseTeacherRoomType;
  return { type, roomsType, currentRoom, apiKey, errorMessage };
};

export default connect(mapStateToProps, {
  closeRoom,
  enterChatTeacher,
  approveAnswer,
})(TeacherRoomMenuForm);
