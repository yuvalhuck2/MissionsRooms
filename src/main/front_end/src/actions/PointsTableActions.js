import { GeneralErrors, PointsTableErrors } from '../locale/locale_heb';
import * as NavPaths from '../navigation/NavPaths'
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';

import {
    POINTS_TO_REDUCE_CHANGED,
    POINTS_TAB_CHANGED,
    RESET_POINTS_TABLE,
    WAIT_FOR_REDUCE,
    UPDATE_ERROR_POINTS_TABLE,
    REDUCE_COMPLETED,
    PERSONAL,
    GROUP,
    CLASSROOM,
  } from '../actions/types';

  import {
    Success,
    Wrong_Key,
    Not_Exist,
    Negative_Points,
    Dont_Have_Permission,
  } from './OpCodeTypes';

  const { server_error,wrong_key_error } = GeneralErrors;

  const {negative_points, permission_problem, points_reduced} = PointsTableErrors;

export const changePoints = (points) => {
    return {
        type: POINTS_TO_REDUCE_CHANGED,
        payload: points,
    };
}

export const tabChanged= ({tab, students, groups, classrooms}) =>{
    switch(tab){
        case PERSONAL:
            return {
                type: POINTS_TAB_CHANGED,
                payload: {tab, list: students},
            }; 
        case GROUP:
            return {
                type: POINTS_TAB_CHANGED,
                payload: {tab, list: groups},
            }; 
        case CLASSROOM:
            return {
                type: POINTS_TAB_CHANGED,
                payload: {tab, list: classrooms},
            }; 
    }
  }

  export const reducePoints = ({apiKey, points, alias}) => {
    return async (dispatch)=>{
        if(points <= 0){
            return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: negative_points });
        }
        try {
            dispatch({type: WAIT_FOR_REDUCE})
            const request = {apiKey, points, alias};
            const res = await API.post(APIPaths.reducePoints,request);
            res
                ? checkReducePointsResponse(res.data, dispatch)
                : dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
        } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
        }
    } 
}

const checkReducePointsResponse = (data, dispatch) => {
    const {reason} = data
    switch (reason) {
      case Negative_Points:
        return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: negative_points }); 
      case Dont_Have_Permission:
        return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: permission_problem }); 
      case Wrong_Key: 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: wrong_key_error });
      case Success:
        alert(points_reduced)
        return dispatch({ type: REDUCE_COMPLETED});
      default:
        return dispatch({ type: UPDATE_ERROR_POINTS_TABLE, payload: server_error });
    }
}

export const handleBack = ({navigation,apiKey}) =>{
    navigation.goBack()
    return {
        type: RESET_POINTS_TABLE,
        payload: apiKey
    }
}