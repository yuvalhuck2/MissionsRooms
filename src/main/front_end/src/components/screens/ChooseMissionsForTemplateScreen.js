import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator, List } from 'react-native-paper';
import { Checkbox } from 'react-native-paper';
import { ChooseMissionsTemplateStrings,AddStrings } from '../../locale/locale_heb';
import { addTemplate,missionsChanged, changeDeterministic,
  changeTrivia, changeStory, changeOpenQuestion, searchMission } from '../../actions/AddRoomTemplateActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import { DETERMINISTIC_NAME, STORY_NAME } from '../../actions/types';

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
  } = ChooseMissionsTemplateStrings;
  
const {
  addButton,
  } = AddStrings;

class ChooseMissionsForTemplateForm extends Component{
    constructor(... args){
        super(... args)
        this.onMissionsChanged=this.onMissionsChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
        this.renderItem=this.renderItem.bind(this)
        this.getMissionPresentation=this.getMissionPresentation.bind(this)
        this.onSearchPress=this.onSearchPress.bind(this)
    }

    onMissionsChanged(mission,toDelete){
      const{missionsToAdd}=this.props;
      list=[];
      if(toDelete){
        list=missionsToAdd.filter((x)=>x.missionId!=mission.missionId);
      }
      else{
        list=[...missionsToAdd,mission]
      }
      this.props.missionsChanged(list)
    }

    onButtonPress() {
        const {name,minimalMissionsToPass,missionsToAdd,type,apiKey,navigation} = this.props;
        this.props.addTemplate( {name,minimalMissionsToPass,missionsToAdd,type,apiKey,navigation} );
    }

    onSearchPress(){
      const {notFilteredMission, deterministic, trivia, story, openQuestion} = this.props;
      this.props.searchMission({notFilteredMission, deterministic, trivia, story, openQuestion})
    }

    getMissionPresentation(mission){
      switch(mission.name){
        case DETERMINISTIC_NAME:
          return deterministic_name+'\n'+question+mission.question[0]
        case STORY_NAME:
          return story_description
        default:
      }
    }

    renderSpinner() {
        return <ActivityIndicator animating={true} color={theme.colors.primary} size='large'/>;
    }

    renderButton(){
        const {loading} = this.props
    
        return loading ? (
          this.renderSpinner()
        ) : (
          <Button
          mode='contained'
          style={styles.button}
          onPress={this.onButtonPress}
          >
          {addButton}
          </Button>
        )
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

      renderItem(mission,toCheck){
          return (
          <Checkbox.Item
                key = {mission.missionId}
                label={this.getMissionPresentation(mission)}
                status={toCheck ? 'checked'  : 'unchecked'}
                onPress={()=>this.onMissionsChanged(mission,toCheck)}
            />
          )
      }

      renderCheckBoxItems(){
        const{presentedMissions,missionsToAdd}=this.props;
        let missionIds=missionsToAdd.map((x)=>x.missionId);
        return(
          presentedMissions.filter((x)=>!missionIds.includes(x.missionId)).map(
            (mission)=>(
              this.renderItem(mission,false)
          ) 
          )
        )
      }

      renderChosenCheckBoxItems(){
        const{missionsToAdd}=this.props;
        return(
          missionsToAdd.map(
            (mission)=>(
              this.renderItem(mission,true)
          ) 
          )
        )
      }

      renderIcon(toCheck){
        return toCheck ? "check-circle-outline"
        : "checkbox-blank-circle-outline";
      }

      renderList(){
        const {deterministic, story, openQuestion, trivia} = this.props
        return (
          <List.Accordion
            title={choose_type}
            >
            <List.Item title={deterministic_label}
              left={props => <List.Icon {...props} icon={this.renderIcon(deterministic)} />}
              onPress = {this.props.changeDeterministic}
              />
            <List.Item title={trivia_label}
              left={props => <List.Icon {...props} icon={this.renderIcon(trivia)} />}
              onPress = {this.props.changeTrivia}/>
            <List.Item title={story_label}
              left={props => <List.Icon {...props} icon={this.renderIcon(story)} />}
              onPress = {this.props.changeStory}/>
            <List.Item title={open_question_label}
              left={props => <List.Icon {...props} icon={this.renderIcon(openQuestion)} />}
              onPress = {this.props.changeOpenQuestion}/>
          </List.Accordion>
        )
      }

    render(){
        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            {this.renderList()}
            <Button
              mode='contained'
              style={styles.button}
              onPress={this.onSearchPress}
              >
              {search}
            </Button>
            {this.renderChosenCheckBoxItems()}
            {this.renderCheckBoxItems()}
            {this.renderButton()}
            {this.renderError()}
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
    const { deterministic, notFilteredMission, story, openQuestion, trivia, name, minimalMissionsToPass,missionsToAdd,type,presentedMissions,apiKey, loading, errorMessage } = state.addRoomTemplate;
    return { deterministic, notFilteredMission, story, openQuestion, trivia, name,minimalMissionsToPass,missionsToAdd,type,presentedMissions,apiKey, loading, errorMessage };
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