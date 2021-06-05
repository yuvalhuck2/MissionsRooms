import React, { Component } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { ActivityIndicator, RadioButton } from "react-native-paper";
import { connect } from "react-redux";
import {
  minimalMissionsChanged,
  nameChanged,
  passToMissions,
  typeChanged,
} from "../../actions/AddRoomTemplateActions";
import { theme } from "../../core/theme";
import { AddRoomTempalteStrings, roomTypes } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const {
  header,
  enter_name,
  enter_type,
  enter_minimal_amount,
  move_to_missions,
} = AddRoomTempalteStrings;

const RoomTypes = roomTypes;

class AddTemplateForm extends Component {
  constructor(...args) {
    super(...args);
    this.onNameChanged = this.onNameChanged.bind(this);
    this.onTypeChanged = this.onTypeChanged.bind(this);
    this.onMinimalAmountChanged = this.onMinimalAmountChanged.bind(this);
    this.onButtonPress = this.onButtonPress.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  onNameChanged(text) {
    this.props.nameChanged(text);
  }

  onTypeChanged(text) {
    this.props.typeChanged(text);
  }

  onMinimalAmountChanged(amount) {
    this.props.minimalMissionsChanged(amount);
  }

  onButtonPress() {
    const {
      name,
      minimalMissionsToPass,
      type,
      allMissions,
      navigation,
    } = this.props;
    this.props.passToMissions({
      name,
      minimalMissionsToPass,
      type,
      allMissions,
      navigation,
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
        {move_to_missions}
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

  renderRadioButtons() {
    let lst = [];
    RoomTypes.map((roomType) => {
      lst.push(
        <RadioButton.Item label={roomType.translate} value={roomType.type} />
      );
    });

    return lst;
  }

  onBackAction() {
    const { navigation } = this.props;
    navigation.goBack();
  }

  render() {
    const { name, minimalMissionsToPass, type } = this.props;
    return (
      <SafeAreaView style={styles.container}>
        <KeyboardAwareScrollView>
          <CustomAppbar backAction={this.onBackAction} />
          <Header>{header}</Header>
          <TextInput
            label={enter_name}
            value={name}
            onChangeText={this.onNameChanged}
            placeholder="שם"
          />

          <TextInput
            label={enter_minimal_amount}
            keyboardType="numeric"
            value={minimalMissionsToPass}
            onChangeText={this.onMinimalAmountChanged}
            placeholder="0"
          />

          <RadioButton.Group
            onValueChange={(value) => this.onTypeChanged(value)}
            value={type}
          >
            {this.renderRadioButtons()}
          </RadioButton.Group>
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
  const {
    name,
    minimalMissionsToPass,
    type,
    allMissions,
    loading,
    errorMessage,
  } = state.addRoomTemplate;
  return {
    name,
    minimalMissionsToPass,
    type,
    allMissions,
    loading,
    errorMessage,
  };
};

export default connect(mapStateToProps, {
  nameChanged,
  minimalMissionsChanged,
  typeChanged,
  passToMissions,
})(AddTemplateForm);
