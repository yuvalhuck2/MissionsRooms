import React, { Component } from "react";
import { Dimensions, SafeAreaView, StyleSheet, Text, View } from "react-native";
import { ActivityIndicator, Button, DataTable } from "react-native-paper";
import { connect } from "react-redux";
import {
  changePoints,
  handleBack,
  reducePoints,
  tabChanged,
} from "../../actions/PointsTableActions";
import { CLASSROOM, GROUP, PERSONAL } from "../../actions/types";
import { theme } from "../../core/theme";
import { PointsTableStrings } from "../../locale/locale_heb";
import CustomAppbar from "../common/CustomAppbar";
import Header from "../common/Header";
import TextInput from "../common/TextInput";

const DeviceWidth = Dimensions.get("window").width;
const backgroundColor = "purple";

const {
  header,
  classroom,
  group,
  personal,
  down_points,
  points_label,
  alias,
  rank,
  down_points_label,
  minus,
} = PointsTableStrings;

class PointsTableForm extends Component {
  constructor(...args) {
    super(...args);
    this.OnTabChanged = this.OnTabChanged.bind(this);
    this.OnPointsChange = this.OnPointsChange.bind(this);
    this.onBackAction = this.onBackAction.bind(this);
  }

  OnPointsChange(points) {
    return this.props.changePoints(points);
  }

  OnTabChanged(tab) {
    const { students, groups, classrooms } = this.props;
    return this.props.tabChanged({ tab, students, groups, classrooms });
  }

  OnButtonPress(alias) {
    const { points, apiKey } = this.props;
    return this.props.reducePoints({ apiKey, points, alias });
  }

  renderTabs() {
    const { tab } = this.props;
    return (
      <DataTable.Row>
        <Button
          style={styles.tabButton}
          icon="account-group"
          mode={tab == CLASSROOM ? "contained" : "outlined"}
          onPress={() => this.OnTabChanged(CLASSROOM)}
        >
          {classroom}
        </Button>
        <Button
          style={styles.tabButton}
          icon="account-multiple"
          mode={tab == GROUP ? "contained" : "outlined"}
          onPress={() => this.OnTabChanged(GROUP)}
        >
          {group}
        </Button>
        <Button
          style={styles.tabButton}
          icon="account"
          mode={tab == PERSONAL ? "contained" : "outlined"}
          onPress={() => this.OnTabChanged(PERSONAL)}
        >
          {personal}
        </Button>
      </DataTable.Row>
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

  renderTextInput() {
    const { points, tab, isStudent } = this.props;
    return tab == PERSONAL && !isStudent
      ? [
          <DataTable.Row key="points">
            <TextInput
              label={down_points_label}
              keyboardType="numeric"
              onChangeText={this.OnPointsChange}
              value={points.toString()}
              placeholder="0"
            />
          </DataTable.Row>,
          <DataTable.Row key="empty"></DataTable.Row>,
        ]
      : null;
  }

  renderDownPointsTab() {
    const { tab, isStudent } = this.props;
    return tab == PERSONAL && !isStudent ? (
      <DataTable.Title numeric>{down_points}</DataTable.Title>
    ) : null;
  }

  renderDataTableHeadlines() {
    return (
      <DataTable.Row>
        <DataTable.Title>{rank}</DataTable.Title>
        <DataTable.Title>{alias}</DataTable.Title>
        <DataTable.Title>{points_label}</DataTable.Title>
        {this.renderDownPointsTab()}
      </DataTable.Row>
    );
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

  renderButton(pointsData) {
    const { canReduceToAll, points, loading } = this.props;
    return loading ? (
      this.renderSpinner()
    ) : (
      <Button
        key="button"
        icon="thumb-down"
        mode="outlined"
        disabled={!canReduceToAll && !pointsData.canReduce}
        compact={true}
        onPress={() => this.OnButtonPress(pointsData.name)}
      >
        {minus + points}
      </Button>
    );
  }

  renderCell(pointsData) {
    const { tab, isStudent } = this.props;
    return tab == PERSONAL && !isStudent ? (
      [
        <DataTable.Cell numeric>{pointsData.points}</DataTable.Cell>,
        <DataTable.Cell key="cell"></DataTable.Cell>,
        this.renderButton(pointsData),
      ]
    ) : (
      <DataTable.Cell>{pointsData.points}</DataTable.Cell>
    );
  }

  renderPointsTable() {
    const { current } = this.props;
    let rankInList = 1;
    let dataRows = [];

    for (; rankInList <= current.length; rankInList++) {
      let pointsData = current[rankInList - 1];
      dataRows.push(
        <DataTable.Row>
          <DataTable.Cell>{rankInList}</DataTable.Cell>
          <DataTable.Cell>{pointsData.name}</DataTable.Cell>
          {this.renderCell(pointsData)}
        </DataTable.Row>
      );
    }
    return dataRows;
  }

  renderDataTable() {
    return (
      <View>
        <Header>{header}</Header>
        <DataTable style={styles.container}>
          {this.renderTextInput()}
          {this.renderTabs()}
          {this.renderDataTableHeadlines()}
          {this.renderPointsTable()}
          {this.renderError()}
        </DataTable>
      </View>
    );
  }

  onBackAction() {
    const { handleBack, navigation, apiKey } = this.props;
    handleBack({ navigation, apiKey });
  }

  render() {
    return (
      <SafeAreaView>
        <CustomAppbar backAction={this.onBackAction} />
        {this.renderDataTable()}
      </SafeAreaView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    flexWrap: "wrap",
    marginTop: 0,
  },
  tabButton: { width: DeviceWidth * 0.31 },
  button: {
    width: DeviceWidth * 0.5,
    height: DeviceWidth * 0.28,
    borderStyle: "solid",
    borderWidth: 1,
    borderColor: "black",
    backgroundColor: backgroundColor,
    textAlign: "left",
    alignContent: "center",
    justifyContent: "center",
  },
  errorTextStyle: {
    fontSize: 22,
    alignSelf: "center",
    color: theme.colors.error,
  },
});

const mapStateToProps = (state) => {
  const {
    points,
    tab,
    students,
    groups,
    classrooms,
    current,
    canReduceToAll,
    apiKey,
    errorMessage,
    loading,
    isStudent,
  } = state.pointsTable;
  return {
    points,
    tab,
    students,
    groups,
    classrooms,
    current,
    canReduceToAll,
    apiKey,
    errorMessage,
    loading,
    isStudent,
  };
};

export default connect(mapStateToProps, {
  changePoints,
  tabChanged,
  reducePoints,
  handleBack,
})(PointsTableForm);
