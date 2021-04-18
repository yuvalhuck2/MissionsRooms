import React, { Component } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { RadioButton } from 'react-native-paper';
import { ChooseTeacherRoomTypeStrings } from '../../locale/locale_heb';
import { passToRoomList } from '../../actions/ChooseRoomTeacherActions';
import { connect } from 'react-redux';
import { theme } from '../../core/theme';
import Button from '../common/Button';
import Header from '../common/Header';

const {
    header,
    classroom,
    personal,
    group,
} = ChooseTeacherRoomTypeStrings;



class ChooseTeacherRoomTypeForm extends Component{
    constructor(... args){
        super(... args)
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
        const {navigation,apiKey ,roomsType}=this.props;

        return (
            <View style={styles.container}>
                <View>
                    <Header>{header}</Header>
                    <Button onPress={() => this.props.passToRoomList({navigation, apiKey, roomsType,type:"Personal"})}
                            style={[styles.button, styles.top_button_marg, styles.left_button_border, styles.top_button_border]}>
                        <Text style={{color: "white"}}> {personal}</Text>
                    </Button>
                    <Button onPress={() => this.props.passToRoomList({navigation, apiKey, roomsType,type:"Group"})}
                            style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>
                        <Text style={{color: "white"}}>{group}</Text>
                    </Button>
                    <Button onPress={() => this.props.passToRoomList({navigation, apiKey, roomsType,type:"Class"})}
                            style={[styles.button, styles.bottom_button_marg, styles.left_button_border]}>

                        <Text style={{color: "white"}}>{classroom}</Text>
                    </Button>
                </View>
            </View>
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
        backgroundColor: theme.colors.primary,
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
    const { type,roomsType, apiKey, errorMessage } = state.ChooseTeacherRoomType;
    return { type,roomsType,apiKey, errorMessage };
};

export default connect(mapStateToProps, {
    passToRoomList,
})(ChooseTeacherRoomTypeForm);