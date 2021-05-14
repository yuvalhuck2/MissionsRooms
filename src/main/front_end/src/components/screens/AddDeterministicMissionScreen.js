import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator } from 'react-native-paper';
import { Checkbox } from 'react-native-paper';
import { AddDeterministicMissionStrings,AddStrings } from '../../locale/locale_heb';
import { questionChanged, answerChanged, typesChanged ,addMission } from '../../actions/AddMissionActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

import {
  DETERMINISTIC,
} from '../../actions/types';

const {
    header,
    enter_question,
    enter_answer,
    personal,
    group,
    classroom,
  } = AddDeterministicMissionStrings;
  
const {
  addButton,
  } = AddStrings;

class AddDeterministicMissionForm extends Component{
    constructor(... args){
        super(... args)
        this.onQuestionChanged=this.onQuestionChanged.bind(this)
        this.onAnswerChanged=this.onAnswerChanged.bind(this)
        this.onTypesChanged=this.onTypesChanged.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
    }

    onQuestionChanged(text){
        this.props.questionChanged(text);
    }

    onAnswerChanged(text) {
        this.props.answerChanged(text);
    }
    
    onTypesChanged(text){
        const{missionTypes} = this.props;
        if (missionTypes.includes(text)){
            x=missionTypes.filter(type=>type!==text)
        }
        else{
            x=[...missionTypes,text]
        }
        this.props.typesChanged(x);
    }

    onButtonPress() {
        const {apiKey, question, points, realAnswer, missionTypes, navigation} = this.props;
        this.props.addMission( {apiKey, question, points, navigation, missionTypes, className: DETERMINISTIC, realAnswer} );
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

    render(){
        const{question,realAnswer,missionTypes}=this.props;

        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            <TextInput
            label={enter_question}
            value={question}
            onChangeText={this.onQuestionChanged}
            placeholder='שאלה'
            />

            <TextInput
            label={enter_answer}
            value={realAnswer}
            onChangeText={this.onAnswerChanged}
            placeholder='תשובה'
            />

            <Checkbox.Item
                label={personal}
                status={missionTypes.includes('Personal') ? 'checked'  : 'unchecked'}
                onPress={()=>this.onTypesChanged('Personal')}
            />

            <Checkbox.Item
                label={group}
                status={missionTypes.includes('Group') ? 'checked'  : 'unchecked'}
                onPress={()=>this.onTypesChanged('Group')}
            />

            <Checkbox.Item
                label={classroom}
                status={missionTypes.includes('Class') ? 'checked'  : 'unchecked'}
                onPress={()=>this.onTypesChanged('Class')}
            />
            
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
    const { apiKey, question, realAnswer, missionTypes, points, loading, errorMessage } = state.addMission;
    return { apiKey, question, realAnswer, missionTypes, points, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    questionChanged,
    answerChanged,
    typesChanged,
    addMission,
  })(AddDeterministicMissionForm);