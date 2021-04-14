import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator, List, Appbar } from 'react-native-paper';
import { Checkbox } from 'react-native-paper';
import { Grades, CloseClassroomStrings } from '../../locale/locale_heb';
import { changeClassNumber, changeGrade, closeClassroom } from '../../actions/CloseClassroomActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';

const {
    enter_class_number,
    choose_grade,
    closeButton,
    header,
  } = CloseClassroomStrings;

const {
    yud,
    yudAlef,
    yudBeit,
} = Grades;

const gradeMap = new Map ([
    [yud, '0'],
    [yudAlef, '1'],
    [yudBeit, '2'],
]) 

class CloseClassromForm extends Component{
    constructor(... args){
        super(... args)
        this.OnChangeGrade=this.onChangeGrade.bind(this)
        this.onButtonPress=this.onButtonPress.bind(this)
        this.onClassNumberChanged=this.onClassNumberChanged.bind(this)
    }

    onClassNumberChanged(number){
      return this.props.changeClassNumber(number);
    }

    onChangeGrade(grade){
      return this.props.changeGrade(gradeMap.get(grade));
    }

    onButtonPress() {
      const {grade, classNumber, apiKey} = this.props;
      return this.props.closeClassroom({grade, classNumber, apiKey});
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
          {closeButton}
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

      renderIcon(toCheck){
          return toCheck ? "check-circle-outline"
          : "checkbox-blank-circle-outline";
      }

      renderIconForGrades(toCheck){
        const {grade} = this.props
        return this.renderIcon( grade == gradeMap.get(toCheck));
      }

      renderGradesList(){
        return (
          <List.Accordion
            key = {choose_grade}
            title={choose_grade}
            >
            <List.Item title={yud}
              left={props => <List.Icon {...props} icon={this.renderIconForGrades(yud)} />}
              onPress = {()=>this.onChangeGrade(yud)}
              />
            <List.Item title={yudAlef}
              left={props => <List.Icon {...props} icon={this.renderIconForGrades(yudAlef)} />}
              onPress = {()=>this.onChangeGrade(yudAlef)}/>
            <List.Item title={yudBeit}
              left={props => <List.Icon {...props} icon={this.renderIconForGrades(yudBeit)} />}
              onPress = {()=>this.onChangeGrade(yudBeit)}/>
          </List.Accordion>
        )
      }

    renderClassNumberTextBox(){
      const {classNumber} = this.props;
      return (
          <TextInput 
          key = {enter_class_number}
          label={enter_class_number}
          keyboardType = 'numeric'
          onChangeText = {this.onClassNumberChanged}
          value = {classNumber}
          placeholder = {enter_class_number}
      /> 
      )
    }


    render(){
      return(
      <KeyboardAwareScrollView style={styles.container}>
          <Appbar.Header styles={styles.bottom}>
           <Appbar.BackAction onPress={() => {this.props.navigation.goBack()}} />
         </Appbar.Header>
        <Header>{header}</Header>
        {this.renderGradesList()}
        {this.renderClassNumberTextBox()}
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
    const { grade, classNumber, apiKey, loading, errorMessage } = state.closeClassroom;
    return { grade, classNumber, apiKey, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    changeClassNumber,
    changeGrade,
    closeClassroom,
  })(CloseClassromForm);