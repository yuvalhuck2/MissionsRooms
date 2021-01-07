import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ActivityIndicator } from 'react-native-paper';
import { Checkbox } from 'react-native-paper';
import { ChooseMissionsTemplateStrings,AddStrings } from '../../locale/locale_heb';
import { addTemplate,missionsChanged } from '../../actions/AddRoomTemplateActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import TextInput from '../common/TextInput';
import { DETERMINISTIC_NAME } from '../../actions/types';

const {
    header,
    deterministic_name,
    question,
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

    getMissionPresentation(mission){
      switch(mission.name){
        case DETERMINISTIC_NAME:
          return deterministic_name+'\n'+question+mission.question[0]
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

    render(){

        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
            {this.renderChosenCheckBoxItems()}
            {this.renderCheckBoxItems()}
            {/* <Checkbox.Item
                label={'personal'}
                status={presentedMissions.includes('Personal') ? 'checked'  : 'unchecked'}
                //onPress={()=>this.onTypesChanged('Personal')}
            />

            <Checkbox.Item
                label={'group'}
                status={presentedMissions.includes('Group') ? 'checked'  : 'unchecked'}
                //onPress={()=>this.onTypesChanged('Group')}
            />

            <Checkbox.Item
                label={'classroom'}
                status={presentedMissions.includes('Class') ? 'checked'  : 'unchecked'}
                //onPress={()=>this.onTypesChanged('Class')}
            /> */}
            
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
      // alignItems: 'center',
      // justifyContent: 'center',
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
    const { name,minimalMissionsToPass,missionsToAdd,type,presentedMissions,apiKey, loading, errorMessage } = state.addRoomTemplate;
    return { name,minimalMissionsToPass,missionsToAdd,type,presentedMissions,apiKey, loading, errorMessage };
  };
  
export default connect(mapStateToProps, {
    missionsChanged,
    addTemplate,
  })(ChooseMissionsForTemplateForm);