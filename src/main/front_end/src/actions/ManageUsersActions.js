import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import { GeneralErrors,TransferError } from '../locale/locale_heb';
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
    UPDATE_ERROR_DELETE_USER, DELETE_USER, UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, PASS_TO_TRANSFER_TEACHER, TRANSFER_GROUP_CHANGED,TRANSFER_CLASSROOM_CHANGED,TRANSFER_TEACHER_SUCCESS,UPDATE_ERROR_TRANSFER_TEACHER
    ,RESET_MANAGE_USERS_TRANSFER,UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT,PASS_TO_TRANSFER_STUDENT,UPDATE_ERROR_TRANSFER_STUDENT,TRANSFER_STUDENT_SUCCESS
} from "./types";

import {
Success,
Not_Exist,
Wrong_Details,
Wrong_Key,
    Teacher_Has_Classroom,
    Empty,Wrong_Group
} from './OpCodeTypes';
import * as NavPaths from "../navigation/NavPaths";

const {
    wrong_key_error,
    not_exist,
    server_error,
    empty_details,
    action_succeeded,
    teacher_has_classroom,
    teacher_has_classroom_transfer,
} = GeneralErrors;

const {
    empty_classroom_error,
    empty_group_error,
    not_exist_classgroup_error,
}=TransferError

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

export const changeDialog = (alias) => {

    return {
        type: PROFILE_CHANGED_MANAGE_USERS,
        payload:alias,
    }

};

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

export const handleBackTransfer = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_MANAGE_USERS_TRANSFER,
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
export const passToTransferGroup = ({apiKey,navigation,alias}) =>{
    return async (dispatch)=>{
        try {
            const res = await API.post(APIPaths.getGradeClassroomAndGroups,{apiKey,alias});
            res
                ? checkPassToTransferGroupResponse(res.data, dispatch,apiKey,navigation)
                : dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT, payload: server_error });
        }
    }
}

const checkPassToTransferGroupResponse= (data,dispatch,apiKey,navigation) =>{
    const {reason,value} =data
    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT, payload: wrong_key_error });
        case Empty:
            alert(empty_group_error)
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT, payload: empty_group_error });
        case Success:
            dispatch({ type: PASS_TO_TRANSFER_STUDENT, payload:{classrooms:value}});
            return navigation.navigate(NavPaths.transferStudent);
        default:
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_STUDENT, payload: server_error });
    }
}

export const passToTransferClassroom=({apiKey,navigation})=>{
    return async (dispatch)=>{
        try {
            const res = await API.post(APIPaths.getClassroomAndGroups,{apiKey});
            res
                ? checkPassToTransferClassroomResponse(res.data, dispatch,apiKey,navigation)
                : dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, payload: server_error });
        }
    }
}

const checkPassToTransferClassroomResponse= (data,dispatch,apiKey,navigation) =>{
    const {reason,value} =data
    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, payload: wrong_key_error });
        case Empty:
            alert(empty_classroom_error);
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, payload: empty_classroom_error });
        case Success:
            dispatch({ type: PASS_TO_TRANSFER_TEACHER, payload:{classrooms:value}});
            return navigation.navigate(NavPaths.transferTeacher);
        default:
            return dispatch({ type: UPDATE_ERROR_PASS_TO_TRANSFER_TEACHER, payload: server_error });
    }
}

export const transferGroupChanged = (group) => {
    return {
        type: TRANSFER_GROUP_CHANGED,
        payload: group,
    };
};

export const transferClassroomChanged = (classroom) => {
    return {
        type: TRANSFER_CLASSROOM_CHANGED,
        payload: classroom,
    };
};

export const transferTeacher=({apiKey,alias,classroomName,groupType,navigation})=>{
    return async (dispatch)=>{
        try {

            const res = await API.post(APIPaths.transferTeacher,{apiKey:apiKey,alias:alias,classroomName:classroomName,groupType:groupType});
            res
                ? checkTransferTeacherClassroomResponse(res.data, dispatch,apiKey,navigation)
                : dispatch({ type: UPDATE_ERROR_TRANSFER_TEACHER, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_TRANSFER_TEACHER, payload: server_error });
        }
    }
}

const checkTransferTeacherClassroomResponse= (data,dispatch,apiKey,navigation) =>{
    const {reason,value} =data
    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_TEACHER, payload: wrong_key_error });
        case Teacher_Has_Classroom:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_TEACHER, payload: teacher_has_classroom_transfer });
        case Success:
            alert(action_succeeded)
            dispatch({ type: TRANSFER_TEACHER_SUCCESS});
            return navigation.navigate(NavPaths.ITMainScreen);
        default:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_TEACHER, payload: server_error });
    }
}

export const transferStudent=({apiKey,alias,classroomName,groupType,navigation})=>{
    return async (dispatch)=>{
        try {

            const res = await API.post(APIPaths.transferStudent,{apiKey:apiKey,alias:alias,classroomName:classroomName,groupType:groupType});
            res
                ? checkTransferStudentClassroomResponse(res.data, dispatch,apiKey,navigation)
                : dispatch({ type: UPDATE_ERROR_TRANSFER_STUDENT, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_TRANSFER_STUDENT, payload: server_error });
        }
    }
}

const checkTransferStudentClassroomResponse= (data,dispatch,apiKey,navigation) =>{
    const {reason,value} =data
    switch (reason) {
        case Wrong_Key:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_STUDENT, payload: wrong_key_error });
        case Wrong_Group:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_STUDENT, payload: not_exist_classgroup_error });
        case Success:
            alert(action_succeeded)
            dispatch({ type: TRANSFER_STUDENT_SUCCESS});
            return navigation.navigate(NavPaths.ITMainScreen);
        default:
            return dispatch({ type: UPDATE_ERROR_TRANSFER_STUDENT, payload: server_error });
    }
}

