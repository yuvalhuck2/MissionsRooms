import React, { Component } from 'react';
import { StyleSheet } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { ChooseMissionToAddStrings } from '../../locale/locale_heb';
import { navigateToMission } from '../../actions/AddMissionActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';
import {DETERMINISTIC} from '../../actions/types'

const {
    header,
    deterministicButton,
  } = ChooseMissionToAddStrings;

const buttons =[
    {name: deterministicButton, type: DETERMINISTIC}
]

class ChooseMissiontoAddForm extends Component{
    constructor(... args){
        super(... args)
        this.onButtonPress=this.onButtonPress.bind(this)
    }

    onButtonPress(type) {
      const{navigation}=this.props;
      this.props.navigateToMission(type,navigation);
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


   render(){
        
        return(
        <KeyboardAwareScrollView style={styles.container}>
            <Header>{header}</Header>
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
    const {} = state.addMission;
    return {};
  };
  
export default connect(mapStateToProps, {
  navigateToMission,
  })(ChooseMissiontoAddForm);