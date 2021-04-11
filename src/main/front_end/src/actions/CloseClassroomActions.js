import API from '../api/API';
import * as APIPaths from '../api/APIPaths';
import { GeneralErrors, CloseClassroomErrors } from '../locale/locale_heb';

import {
    CLASS_NUMBER_CLASSROOM_CHANGED,
    GRADE_CLASSROOM_CHANGED,
    UPDATE_CLOSE_CLASSROOM_ERROR,
    CLOSE_CLASSROOM_RESET,
    WAIT_FOR_CLOSE_CLASSROOM,
  } from '../actions/types';

import { 
    Success,
    Not_Exist_Classroom,
    Wrong_Key,
    Not_Exist,
    Has_Students,
} from './OpCodeTypes';

  const {
    server_error,
    wrong_key_error,
} = GeneralErrors;

const {
    wrong_class,
    classroom_not_empty,
    classroom_closed,
} = CloseClassroomErrors;

export const changeClassNumber = (classNumber) => {
    return {
        type: CLASS_NUMBER_CLASSROOM_CHANGED,
        payload: classNumber,
    }
}

export const changeGrade = (grade) => {
    return {
        type: GRADE_CLASSROOM_CHANGED,
        payload: grade,
    } 
}

export const closeClassroom = ({grade, classNumber, apiKey}) => {
    return async (dispatch)=>{
        if(grade.trim() === ''){
            return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: wrong_class})
        }
        if(classNumber.trim() === ''){
            return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: wrong_class})
        }
        let name = grade+"="+classNumber
        try {
            dispatch({type: WAIT_FOR_CLOSE_CLASSROOM})
            const request = {name, apiKey};
            const res = await API.post(APIPaths.closeClassroom,request);
            res
                ? checkCloseClassroomRespnose(res.data, dispatch)
                : dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: server_error });
        }
    }
}

const checkCloseClassroomRespnose= (data, dispatch) =>{
    const {reason} = data
    switch (reason) {
      case Wrong_Key:
      case Not_Exist:
        return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: wrong_key_error }); 
      case Not_Exist_Classroom:
        return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: wrong_class });
      case Has_Students:
        return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: classroom_not_empty});
      case Success:
        alert(classroom_closed)
        return dispatch({ type: CLOSE_CLASSROOM_RESET});
      default:
        return dispatch({ type: UPDATE_CLOSE_CLASSROOM_ERROR, payload: server_error });
    }
  }