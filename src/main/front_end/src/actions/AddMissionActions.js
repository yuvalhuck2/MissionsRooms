import {
    QUESTION_CHANGED,
    ANSWER_CHANGED,
    TYPES_CHANGED,
    ADD_MISSON,
    DETERMINISTIC,
    UPDATE_ERROR,
  } from '../actions/types';
  
export const questionChanged = (text) => {
    return {
      type: QUESTION_CHANGED,
      payload: text,
    };
  };
  
  export const answerChanged = (text) => {
    return {
      type: ANSWER_CHANGED,
      payload: text,
    };
  };

  export const typesChanged = (list) => {
    return {
      type: TYPES_CHANGED,
      payload: list,
    };
  };

  export const navigateToMission = (type) => {
    return {
      type,
      payload:type,
    };
  };
  
  export const addMission = (mission) => {
      console.log(mission)
    return async (dispatch) => {
        dispatch({ type: ADD_MISSON });
        alert(mission)
        //const res = await API.post('/UserAuth', { alias: email, password });
        // console.log(res.data);
        // if (res) {
        //   alert("wow")
        // }
    };
  };