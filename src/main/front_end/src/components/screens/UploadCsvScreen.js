import React, { Component } from "react";
import { Dimensions, SafeAreaView, StyleSheet, Text, View } from "react-native";
import { connect } from "react-redux";
import { handleBack, onPickFile, onRestart, onSendFiles } from "../../actions";
import { uploadStrings } from "../../locale/locale_heb";
import Button from "../common/Button";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const { header, choose_btn, approve_btn, restart_btn } = uploadStrings;

const DeviceWidth = Dimensions.get("window").width;
class UploadCsvForm extends Component {
  constructor(...args) {
    super(...args);
    this.onPickFile = this.onPickFile.bind(this);
    this.onRestart = this.onRestart.bind(this);
    this.onSendFiles = this.onSendFiles.bind(this);
  }

  onRestart() {
    this.props.onRestart();
  }

  onPickFile() {
    this.props.onPickFile();
  }

  async onSendFiles() {
    this.props.onSendFiles({
      files: this.props.files,
      apiKey: this.props.apiKey,
    });
  }

  onBackPress() {
    const { navigation, apiKey } = this.props;
    this.props.handleBack({ navigation, apiKey });
  }

  render() {
    const { text } = this.props;

    return (
      <SafeAreaView
        contentContainerStyle={styles.wrapper}
        style={styles.container}
      >
        <CustomAppbar backAction={this.onBackPress} />
        <Header>{header}</Header>
        {this.renderError()}
        {this.renderSuccess()}
        <View style={styles.row}>
          <TextInput style={styles.files_label} editable={false} value={text} />
          <Button
            style={styles.button}
            mode="contained"
            onPress={this.onPickFile.bind(this)}
          >
            {choose_btn}
          </Button>
          <Button
            style={styles.button}
            mode="contained"
            onPress={this.onRestart.bind(this)}
          >
            {restart_btn}
          </Button>
          <Button
            style={styles.button}
            mode="contained"
            onPress={this.onSendFiles.bind(this)}
          >
            {approve_btn}
          </Button>
        </View>
      </SafeAreaView>
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
  renderSuccess() {
    const { successMessage } = this.props;
    if (successMessage && successMessage !== "") {
      return (
        <View>
          <Text style={styles.successTextStyle}>{successMessage}</Text>
        </View>
      );
    }
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
    width: "100%",
    maxWidth: 340,
    alignSelf: "center",
  },
  row: {
    flex: 1,
    marginTop: 0,
    justifyContent: "center",
    alignItems: "center",
  },
  files_label: {
    width: DeviceWidth * 0.8,
  },
  button: {
    width: DeviceWidth * 0.5,
    height: DeviceWidth * 0.12,
  },
  wrapper: {
    justifyContent: "center",
    alignItems: "center",
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: "red",
  },
  successTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: "green",
  },
  bottom: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
  },
});

const mapStateToProps = (state) => {
  const { text, files, errorMessage, successMessage, apiKey } = state.IT;
  return { text, files, errorMessage, successMessage, apiKey };
};

export default connect(mapStateToProps, {
  onRestart,
  onPickFile,
  onSendFiles,
  handleBack,
})(UploadCsvForm);
