import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import { GeneralErrors } from '../locale/locale_heb';
import { 
    SEARCH_CHANGED_MANAGE_USERS,
    MANAGED_USERS_CHANGED,
    RESET_MANAGE_USERS,
    PROFILE_CHANGED_MANAGE_USERS,
    FIRST_NAME_CHANGED_MANAGE_USERS,
    UPDATE_ERROR_MANAGE_USERS,
    WAIT_MANAGE_USERS,
    CHANGE_ENABLE_FIRST_NAME,
    CHANGE_ENABLE_LAST_NAME,
    LAST_NAME_CHANGED_MANAGE_USERS,
    ACTION_FINISHED_MANAGED_USERS,
    UPDATE_ERROR_DELETE_USER,DELETE_USER,
} from "./types";

import {
Success,
Not_Exist,
Wrong_Details,
Wrong_Key,
    Teacher_Has_Classroom,
} from './OpCodeTypes';

const {
    wrong_key_error,
    not_exist,
    server_error,
    empty_details,
    action_succeeded,
    teacher_has_classroom,
} = GeneralErrors;

export const searchChanged= (text) => {
    return {
        type: SEARCH_CHANGED_MANAGE_USERS,
        payload: text,
    };
} 

export const filterUsers = (search, users) =>{
    return {
        type: MANAGED_USERS_CHANGED,
        payload: users.filter((user)=> user.alias.includes(search))
    }
}

export const changeDialog = (alias) =>{
    return {
        type: PROFILE_CHANGED_MANAGE_USERS,
        payload: alias,
    }
}

export const firstNameChanged = (text) => {
    return {
        type: FIRST_NAME_CHANGED_MANAGE_USERS,
        payload: text,
    }
} 

export const lastNameChanged = (text) => {
    return {
        type: LAST_NAME_CHANGED_MANAGE_USERS,
        payload: text,
    }
}

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_MANAGE_USERS,
        payload: apiKey,
    }
}

export const changeEnableFirstName = (isEnableFirstName) => {
    return {
        type: CHANGE_ENABLE_FIRST_NAME,
        payload: isEnableFirstName,
    }
}

export const changeEnableLastName = (isEnableLastName) => {
    return {
        type: CHANGE_ENABLE_LAST_NAME,
        payload: isEnableLastName,
    }
}

export const editUserDetails = ({apiKey,firstName,lastName,alias}) => {
    return async (dispatch)=>{
        if(firstName.trim()=="" && lastName.trim()==""){
            return dispatch ({type:UPDATE_ERROR_MANAGE_USERS, payload: empty_details})
        }
        try {
            dispatch({ type: WAIT_MANAGE_USERS});
            const solution={apiKey,firstName,lastName,alias};
            const res = await API.post(APIPaths.updateDetails,solution);
            res
              ? checkEditUserDetailRespnose(res.data, dispatch,firstName,lastName)
              : dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: server_error });
          } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: server_error });
          }
    }
  }

  const checkEditUserDetailRespnose= (data,dispatch,firstName,lastName) =>{
    const {reason} =data
    switch (reason) {
      case Wrong_Details:
        return dispatch ({type:UPDATE_ERROR_MANAGE_USERS, payload: empty_details})
      case Wrong_Key:
        return dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: wrong_key_error }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_MANAGE_USERS, payload: not_exist });
      case Success:
          alert(action_succeeded)
        return dispatch({ type: ACTION_FINISHED_MANAGED_USERS, payload:{firstName,lastName}});
      default:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
    }
  }


export const deleteUser = ({apiKey,alias}) =>{
    return async (dispatch)=> {
        try {

            const res = await API.post(APIPaths.deleteUser,{apiKey,alias});
            res
                ? checkDeleteUserResponse(res.data, dispatch,alias)
                : dispatch({ type: UPDATE_ERROR_DELETE_USER, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_DELETE_USER, payload: server_error });
        }

    }

}
const checkDeleteUserResponse= (data,dispatch,alias) =>{
    const {reason} =data

    switch (reason) {
        case Teacher_Has_Classroom:
            alert(teacher_has_classroom);
            return dispatch ({type:UPDATE_ERROR_DELETE_USER, payload: teacher_has_classroom})
        case Wrong_Key:
            return dispatch({ type: UPDATE_ERROR_DELETE_USER, payload: wrong_key_error });
        case Success:
            alert(action_succeeded)
            return dispatch({ type: DELETE_USER, payload:{alias:alias}});
        default:
            return dispatch({ type: UPDATE_ERROR_DELETE_USER, payload: server_error });
    }
}
export const passToTransferGroup = () =>{}