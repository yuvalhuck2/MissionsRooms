import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { RadioButton } from "react-native-paper";
import { connect } from "react-redux";
import {
  passToSolveMission,
  roomChanged,
} from "../../actions/ChooseStudentRoomActions";
import {
  DETERMINISTIC_NAME,
  OPEN_QUESTION_NAME,
  STORY_NAME,
  TRIVIA_NAME,
} from "../../actions/types";
import { theme } from "../../core/theme";
import {
  ChooseMissionsTemplateStrings,
  ChooseStudentRoomStrings,
  roomTypes,
} from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";

const {
  header,
  room_name,
  room_type,
  mission_presentation,
  solve,
  no_rooms,
} = ChooseStudentRoomStrings;

const {
  deterministic_name,
  question,
  open_question_name,
  trivia_question_name,
  story_description,
} = ChooseMissionsTemplateStrings;

class ChooseStudentRoomForm extends Component {
  constructor(...args) {
    super(...args);
    this.onRoomChanged = this.onRoomChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  onRoomChanged(value) {
    this.props.roomChanged(value);
  }

  onButtonPress() {
    const { currentRoom, navigation, apiKey } = this.props;

    this.props.passToSolveMission({ currentRoom, navigation, apiKey });
  }

  renderButton() {
    const { rooms } = this.props;
    if (rooms.size > 0) {
      return (
        <Button
          mode="contained"
          style={styles.button}
          onPress={this.onButtonPress}
        >
          {solve}
        </Button>
      );
    } else {
      return (
        <View>
          <Text style={styles.errorTextStyle}>{no_rooms}</Text>
        </View>
      );
    }
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

  getType(type) {
    return roomTypes
      .filter((roomType) => roomType.type == type)[0]
      .translate.slice(0, -1);
  }

  renderRoom(room) {
    return (
      room_name +
      room.name +
      "\n" +
      room_type +
      this.getType(room.roomType) +
      "\n" +
      mission_presentation +
      "\n\t\t" +
      this.getMissionPresentation(room.currentMission)
    );
  }

  renderRadioButtons() {
    const { rooms } = this.props;
    let lst = [];
    rooms.forEach((room, roomId) => {
      lst.push(
        <RadioButton.Item
          label={this.renderRoom(room)}
          value={room}
          color={theme.colors.primary}
          labelStyle={{
            fontWeight: "bold",
            color: theme.colors.secondary,
          }}
        />
      );
    });

    return lst;
  }

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    const { currentRoom } = this.props;
    return (
      <SafeAreaView style={styles.container}>
        <CustomAppbar backAction={this.onBackAction} />
        <Header>{header}</Header>
        <RadioButton.Group
          onValueChange={(value) => this.onRoomChanged(value)}
          value={currentRoom}
        >
          {this.renderRadioButtons()}
        </RadioButton.Group>
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
    // alignItems: 'center',
    // justifyContent: 'center',
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
  const { rooms, currentRoom, apiKey, errorMessage } = state.ChooseStudentRoom;
  return { rooms, currentRoom, apiKey, errorMessage };
};

export default connect(mapStateToProps, {
  roomChanged,
  passToSolveMission,
})(ChooseStudentRoomForm);
