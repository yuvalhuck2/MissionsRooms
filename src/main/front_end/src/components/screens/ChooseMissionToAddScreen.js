import React, { Component } from 'react';
import { StyleSheet } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ChooseMissionToAddStrings } from '../../locale/locale_heb';
import { navigateToMission, changePoints, addMission } from '../../actions/AddMissionActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import {DETERMINISTIC, OPEN_QUESTION, STORY_MISSION} from '../../actions/types'
import TextInput from '../common/TextInput';

const {
    header,
    deterministicButton,
    OpenAnswerMissionButton,
    StoryMissionButton,
    enter_points,
  } = ChooseMissionToAddStrings;

const buttons =[
    {name: deterministicButton, type: DETERMINISTIC},
    {name: OpenAnswerMissionButton , type: OPEN_QUESTION},
    {name: StoryMissionButton, type: STORY_MISSION}
]

const storyTypes = ['Group', 'Class']

class ChooseMissiontoAddForm extends Component{
    constructor(... args){
        super(... args)
        this.onButtonPress=this.onButtonPress.bind(this)
        this.onPointsChanged = this.onPointsChanged.bind(this)
    }

    onButtonPress(type) {
      const{navigation, points, apiKey}=this.props;
      if( type == STORY_MISSION ){
          this.props.addMission({apiKey, points, navigation, missionTypes: storyTypes, className: type})
      }
      return this.props.navigateToMission(type, navigation, points);
    }

    onPointsChanged(points) {
      this.props.changePoints(points);
    }

    renderButtons(){
        
        return  (
        buttons.map(
            (btn)=>(
                <Button
                    mode='contained'
                    style={styles.button}
                    onPress={()=>this.onButtonPress(btn.type)}
                >
                {btn.name}
                </Button>
                    )
            )
        )
      }

    renderTextBox(){
      const {points} = this.props;
      return (
        <TextInput
          label={enter_points}
          value={points}
          onChangeText={this.onPointsChanged}
          placeholder='0'
          keyboardType = 'numeric'
        />
      );
    }

   render(){
        
        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            {this.renderTextBox()}
            {this.renderButtons()}
        </KeyboardAwareScrollView>
    );
        
    }
}

const styles = StyleSheet.create({
    container: {
      flex: 1,
      padding: 20,
      width: '100%',
      maxWidth: 340,
      alignSelf: 'center',
    },
    button: {
      marginTop: 24,
    },
    row: {
      flexDirection: 'row',
      marginTop: 10,
    },
    link: {
      fontWeight: 'bold',
      color: theme.colors.primary,
    },
    errorTextStyle: {
      fontSize: 25,
      alignSelf: 'center',
      color: theme.colors.error,
    },
  });

const mapStateToProps = (state) => {
    const {points, apiKey} = state.addMission;
    return {points, apiKey};
  };
  
export default connect(mapStateToProps, {
  navigateToMission,
  changePoints,
  addMission,
  })(ChooseMissiontoAddForm);