import React, { Component } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator, Appbar, List, Dialog, Portal, Searchbar } from 'react-native-paper';
import { connect } from 'react-redux';
import { handleBack, onMissionPress } from '../../actions/WatchAllOpenAnswersActions';
import { theme } from '../../core/theme';
import { WatchAllOpenQuestionMissions } from '../../locale/locale_heb';
import Header from '../common/Header';


const {
  mission_title,
  title
} = WatchAllOpenQuestionMissions;

class WatchAllAnswersForm extends Component {
  constructor(...args) {
    super(...args);

    this.onBackPress = this.onBackPress.bind(this);
    this.onMissionPress = this.onMissionPress.bind(this);
  }


  onButtonPress() {
    const { apiKey, message, profile } = this.props;
    this.props.sendMessage({ apiKey, message, profile });
  }

  onBackPress() {
    const { navigation, apiKey } = this.props;
    this.props.handleBack({ navigation, apiKey });
  }

  onMissionPress(solutionData) {
    const {navigation, apiKey, roomId} = this.props;
    this.props.onMissionPress({navigation, roomId, solutionData, apiKey})
  }

  renderListItems() {
    const { openAnswers } = this.props;
    return (
      openAnswers.map((mission, index) =>
        <List.Item 
          right={props => <View style={styles.item_view}>
            <Text style={styles.item_text}>{mission_title + index}</Text>
            <List.Icon {...props} icon="account" />
          </View>}
          key={mission.missionId}
          onPress={() => this.onMissionPress(mission)}
        />
      )
    )
  }


  render() {
    const { roomName } = this.props;
    return (
      <KeyboardAwareScrollView style={styles.container}>
        <Appbar.Header styles={styles.bottom}>
          <Appbar.BackAction onPress={() => { this.onBackPress() }} />
        </Appbar.Header>
        <Header>{title + roomName}</Header>
        <View style={styles.list_item}>{this.renderListItems()}</View>
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
    // alignItems: 'center',
    // justifyContent: 'center',
  },
  button: {
    marginTop: 0,
    width: 100,
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
  item_view: {
    flexDirection: 'row',
  },
  item_text: {
    fontSize: 20,
    alignSelf: 'center',

  }
});

const mapStateToProps = (state) => {
  //const { search, message, profile, allUsers, presentedUsers, apiKey, errorMessage, loading } = state.WatchProfile;
  //return { search, message, profile, allUsers, presentedUsers, apiKey, errorMessage, loading };
  openAnswers = [
    {
      "missionId": "open1",
      "roomId": null,
      "openAnswer": "answerrrr",
      "hasFile": true,
      "missionQuestion": "שאלה"
    }
  ];
  roomName = "ss";
  roomId = "rid1";
  return { openAnswers, roomName, roomId};
};

export default connect(mapStateToProps, {
  handleBack,
  onMissionPress
})(WatchAllAnswersForm);
