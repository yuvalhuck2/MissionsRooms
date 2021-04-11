import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors, addUserErrors } from '../locale/locale_heb';

import {
    ALIAS_USER_CHANGED,
    FIRSTNAME_USER_CHANGED,
    LASTNAME_USER_CHANGED,
    GRADE_USER_CHANGED,
    CLASS_NUMBER_CHANGED,
    CLASSGORUP_USER_CHANGED,
    SUPERVISOR_USER_CHANGED,
    UPDATE_ADD_USER_ERROR,
    ADD_USER_RESET,
    WAIT_FOR_ADD_USER,
  } from '../actions/types';

import { 
    Wrong_Alias,
    Wrong_First_Name,
    Wrong_Last_Name,
    Already_Exist,
    Success,
    Wrong_Classroom,
    Not_Exist_Classroom,
    Wrong_Group,
    Wrong_Key,
    Not_Exist,
} from './OpCodeTypes';

  const {
    server_error,
    wrong_key_error,
} = GeneralErrors;

const {
    already_exist,
    wrong_alias,
    wrong_first_name,
    wrong_last_name,
    wrong_class,
    wrong_group,
    user_added,
} = addUserErrors

export const changeClassNumber = (classNumber) => {
    return {
        type: CLASS_NUMBER_CHANGED,
        payload: classNumber,
    }
}

export const changeGrade = (grade) => {
    return {
        type: GRADE_USER_CHANGED,
        payload: grade,
    } 
}

export const changeGroup = (group) => {
    return {
        type: CLASSGORUP_USER_CHANGED,
        payload: group,
    }  
}

export const changeAlias = (alias) => {
    return {
        type: ALIAS_USER_CHANGED,
        payload: alias,
    } 
}

export const changeFirstName = (name) => {
    return {
        type: FIRSTNAME_USER_CHANGED,
        payload: name,
    } 
}

export const changeLastName = (name) => {
    return {
        type: LASTNAME_USER_CHANGED,
        payload: name,
    } 
}

export const changeSupervisor = () => {
    return {
        type: SUPERVISOR_USER_CHANGED,
    } 
}

export const addUser = ({alias, firstName, lastName, grade, classNumber, classGroup,
    isStudent, isSupervisor, apiKey}) => {

    if(alias.trim() === ''){
        return { type: UPDATE_ADD_USER_ERROR, payload: wrong_alias}
    }
    if(firstName.trim() === ''){
        return { type: UPDATE_ADD_USER_ERROR, payload: wrong_first_name}
    }
    if(lastName.trim() === ''){
        return { type: UPDATE_ADD_USER_ERROR, payload: wrong_last_name}
    }
    if(isStudent){
        return addStudent({alias, firstName, lastName, grade, classNumber, classGroup,
            apiKey})
    }
    else{
        return addTeacher({alias, firstName, lastName, isSupervisor, apiKey})
    }

}

const addStudent = ({alias, firstName, lastName, grade, classNumber, classGroup,
    apiKey}) => {
    return async (dispatch)=>{
        if(grade.trim() === ''){
            return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_class})
        }
        if(classNumber.trim() === ''){
            return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_class})
        }
        if(classGroup.trim() === ''){
            return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_group})
        }
        let classroom = grade+"="+classNumber
        try {
            dispatch({type: WAIT_FOR_ADD_USER})
            const request = {alias, firstName, lastName, classroom, groupType: classGroup, apiKey};
            const res = await API.post(APIPaths.addStudent,request);
            res
                ? checkAddStudentResponse(res.data, dispatch,apiKey)
                : dispatch({ type: UPDATE_ADD_USER_ERROR, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: server_error });
        }
    }
}

const checkAddStudentResponse = (data, dispatch, apiKey) => {
    const {reason} = data
    switch (reason) {
      case Wrong_Classroom:
      case Not_Exist_Classroom:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_class });
      case Wrong_Group:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_group });
    default:
        return checkAddTeacherResponse(data, dispatch, apiKey);
    }
}

const addTeacher = ({alias, firstName, lastName, isSupervisor, apiKey}) => {
    return async (dispatch)=>{
        try {
            dispatch({type: WAIT_FOR_ADD_USER})
            const request = {alias, firstName, lastName, isSupervisor, apiKey};
            const res = await API.post(APIPaths.addTeacher,request);
            res
                ? checkAddTeacherResponse(res.data, dispatch,apiKey)
                : dispatch({ type: UPDATE_ADD_USER_ERROR, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: server_error });
        }
    }
}


const checkAddTeacherResponse = (data, dispatch, apiKey) => {
    const {reason} = data
    switch (reason) {
      case Wrong_Alias:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_alias });
      case Wrong_First_Name:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_first_name });
      case Wrong_Last_Name:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_last_name });
      case Already_Exist:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: already_exist }); 
      case Wrong_Key:  
      case Not_Exist:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: wrong_key_error });
      case Success:
        alert(user_added)
        return dispatch({ type: ADD_USER_RESET, payload:apiKey});
      default:
        return dispatch({ type: UPDATE_ADD_USER_ERROR, payload: server_error });
    }
}