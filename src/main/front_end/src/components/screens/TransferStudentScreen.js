import {Component} from "react";
import { connect } from 'react-redux';
import {addSuggestion, suggestionChange} from "../../actions";
import {KeyboardAwareScrollView} from "react-native-keyboard-aware-scroll-view";
import Header from "../common/Header";
import TextInput from "../common/TextInput";
import React from "react";
import {theme} from "../../core/theme";
import { View, Picker, StyleSheet,Text } from "react-native";
import RNPickerSelect from 'react-native-picker-select';
import {transferClassroomChanged,transferGroupChanged,transferStudent,handleBackTransfer}from '../../actions/ManageUsersActions';
import {TransferStudentStrings} from '../../locale/locale_heb';
import Button from "../common/Button";
import { ActivityIndicator,Appbar, List, Dialog, Portal, Searchbar, DataTable, Switch} from 'react-native-paper';



const {
    header,
    select_classroom,
    select_group,
    ok,
} = TransferStudentStrings;

class TransferStudentForm extends Component {
    constructor(...args) {
        super(...args);
        this.onTransferClassroomChanged=this.onTransferClassroomChanged.bind(this);
        this.onTransferGroupChanged=this.onTransferGroupChanged.bind(this);
        this.onButtonPress=this.onButtonPress.bind(this);
        this.onBackPress=this.onBackPress.bind(this);
    }

    onTransferClassroomChanged(value){

        this.props.transferClassroomChanged(value);

    }

    onTransferGroupChanged(value){

        this.props.transferGroupChanged(value)

    }

    onButtonPress(){
        const {chosenClassroom,chosenGroup,currUser,apiKey,navigation}=this.props;
        this.props.transferStudent({apiKey:apiKey,alias:currUser.alias,classroomName:chosenClassroom,groupType:chosenGroup,navigation:navigation})
    }

    onBackPress(){
        const {navigation,apiKey} =this.props;
        this.props.handleBackTransfer({navigation,apiKey});
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

    renderGroupPickerItems =() =>{
        const {chosenClassroom,allClassrooms}=this.props;
        if(chosenClassroom=='')
            return;
            let groups = allClassrooms.filter((classAndGroups) =>
                classAndGroups.classroom == chosenClassroom
            )[0].groupTypes;

            let groupItems = groups.map((g) => {
                return <Picker.Item label={g} value={g}/>
            });
            return groupItems;

    }

    renderClassroomPickerItems=()=>{
        const{allClassrooms}=this.props;
            var lst = allClassrooms.map((val) => val.classroom)
            var items = allClassrooms.map((val) => {
                return <Picker.Item label={val.name} value={val.classroom}/>
            })

            return items;


    };


    getHeaderPresentation=()=>{
        const {currUser}=this.props;
        if(currUser!==undefined &&currUser!=='') {
            return header + ": " + currUser.firstName + " " + currUser.lastName;
        }
    };


    renderSpinner=()=> {
        return <ActivityIndicator animating={true} color={theme.colors.primary} size='large'/>;
    }

    renderButton=()=>{
        const {loading} = this.props

        return loading ? (
            this.renderSpinner()
        ) : (
            <Button
                mode='contained'
                style={styles.button}
                onPress={this.onButtonPress}
            >
                {ok}
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
        const{allClassrooms,chosenClassroom,chosenGroup}=this.props;

        return(
            <KeyboardAwareScrollView style={styles.container}>
                <Appbar.Header styles={styles.bottom}>
                    <Appbar.BackAction onPress={() => {this.onBackPress()}} />
                </Appbar.Header>

                <View style={styles.container2}>
                <Header>{this.getHeaderPresentation()}</Header>
                <View>
                    <Picker
                        mode='dropdown'
                        selectedValue={chosenClassroom}
                        onValueChange={(itemValue, itemIndex) =>
                            this.onTransferClassroomChanged(itemValue)
                        }>
                        <Picker.Item label={select_classroom} value="" />
                        {this.renderClassroomPickerItems()}
                    </Picker>
                </View>
                <View>
                    <Picker
                        mode='dropdown'
                        selectedValue={chosenGroup}
                        onValueChange={(itemValue, itemIndex) =>
                            this.onTransferGroupChanged(itemValue)
                        }>
                        <Picker.Item label={select_group} value="" />
                        {this.renderGroupPickerItems()}

                    </Picker>
                </View>
                {this.renderButton()}
                    {this.renderError()}
                </View>
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
    container2: {
        flex: 1,
        padding: 20,
        width: '100%',
        alignSelf: 'center',
        marginTop:-50,
    },
    button: {
        marginTop: 20,
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
});

const mapStateToProps = (state) => {
    const {  apiKey, loading,allClassrooms,chosenClassroom ,chosenGroup,currUser,errorMessage} = state.manageUsers;
    return {  apiKey, loading,allClassrooms ,chosenClassroom ,chosenGroup,currUser,errorMessage};
};

export default connect(mapStateToProps,{
    transferClassroomChanged,
    transferGroupChanged,
    transferStudent,
    handleBackTransfer,
})(TransferStudentForm);