import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator,RadioButton, Searchbar } from 'react-native-paper';
import { ChooseTempalteStrings,AddStrings,ChooseMissionsTemplateStrings } from '../../locale/locale_heb';
import { templateChanged,addRoom, filterTemplates, searchChanged } from '../../actions/AddRoomActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { DETERMINISTIC_NAME, STORY_NAME, OPEN_QUESTION_NAME, TRIVIA_NAME } from '../../actions/types'; 

const {
    header,
    template_name,
    minimal_missions,
    missions_presentation,
    no_tempaltes,
    filter_by_name,
  } = ChooseTempalteStrings;

const {
    addButton
}= AddStrings

const {
    deterministic_name,
    question,
    story_description,
    open_question_name,
    trivia_question_name,
  } = ChooseMissionsTemplateStrings;

class ChooseTemplatesForm extends Component{
    constructor(... args){
        super(... args)
        this.onTemplateChanged=this.onTemplateChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
        this.onFilterTemplates=this.onFilterTemplates.bind(this);
        this.onSearchChanged=this.onSearchChanged.bind(this);
    }
    
    onTemplateChanged(text){
        this.props.templateChanged(text);
    }

    onSearchChanged(text){
      this.props.searchChanged(text)
    }

    onFilterTemplates(){
      const {search, NotFilteredTemplates} = this.props;
      this.props.filterTemplates({search, NotFilteredTemplates})
    }

    onButtonPress() {
        const {roomName,participantKey,roomTemplate,bonus,type,apiKey,navigation} = this.props;
        
        this.props.addRoom( {roomName,participantKey,roomTemplateId:roomTemplate.id,apiKey,bonus,type,navigation} );
    }

    renderSpinner() {
        return <ActivityIndicator animating={true} color={theme.colors.primary} size='large'/>;
    }

    renderButton(){
        const {loading,presentedTemplates,type} = this.props
    
        return loading ? (
          this.renderSpinner()
        ) : presentedTemplates.length==0 && type!==""?
        <View>
          <Text style={styles.errorTextStyle}>{no_tempaltes}</Text>
        </View>
        :(
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

      getMissionPresentation(mission){ 
        switch(mission.name){
          case DETERMINISTIC_NAME:
            return deterministic_name+'\n\t\t'+question+mission.question[0]
          case STORY_NAME:
            return story_description
          case OPEN_QUESTION_NAME:
            return open_question_name+'\n'+question+mission.question[0];
          case TRIVIA_NAME:
            return trivia_question_name;
          default:
              return ""
        }
      }

      renderTemplate(template){
          return template_name+template.name+'\n'+
          minimal_missions+template.minimalMissionsToPass+'\n'+
          missions_presentation+template.missions.reduce(
             (acc,mis)=> {return acc+'\n\t\t'+this.getMissionPresentation(mis)} 
          ,'')
      }

      renderRadioButtons(){
          const {presentedTemplates}=this.props;
          let lst=[]
          presentedTemplates.map((template)=>{
            lst.push(
                <RadioButton.Item 
                    label={this.renderTemplate(template)}
                    value={template}
                    color={theme.colors.primary}
                    labelStyle={{
                        fontWeight: 'bold',
                        color: theme.colors.secondary,
                      }}
                      
                 />)
          })
          
          return lst
      }

    render(){
        const {roomTemplate, search}=this.props;
        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            <Searchbar
              label={filter_by_name}
              value={search}
              onChangeText={this.onSearchChanged}
              icon = {"magnify"}
              onIconPress={this.onFilterTemplates}
              placeholder={filter_by_name}
            />
            <RadioButton.Group 
            onValueChange={(value) => this.onTemplateChanged(value)} 
            value={roomTemplate}>
                {this.renderRadioButtons()}
            </RadioButton.Group>
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
    const { search, NotFilteredTemplates, roomName,participantKey,roomTemplate,bonus,type,presentedTemplates,apiKey, loading, errorMessage } = state.addRoom;
    return { search, NotFilteredTemplates, roomName,participantKey,roomTemplate,bonus,type,presentedTemplates,apiKey, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    templateChanged,
    addRoom,
    filterTemplates,
    searchChanged
  })(ChooseTemplatesForm);