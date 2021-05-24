import { StoryStrings,GeneralErrors } from '../locale/locale_heb';
import * as APIPaths from '../api/APIPaths'
import API from '../api/API';
import {
    CURRENT_ANSWER_STORY_CHANGED,
    UPDATE_ERROR_SOLVE_STORY,
    STORY_MISSION_SEND,
    FINISH_MISSION,
    CHANGE_STORY_IN_CHARGE,
  } from './types';
 
  import {
    Success,
    Wrong_Key,
    Not_Exist,
  } from './OpCodeTypes';
  
const {
    wrong_sentence,
    sentence_added,
  } = StoryStrings;

const {
    server_error,
    wrong_key_error,
    student_not_exist,
  }=GeneralErrors

  export const sentenceChanged= (text) =>{
    return {
      type: CURRENT_ANSWER_STORY_CHANGED,
      payload: text,
    };
  }

  export const sendStoryAnswer = ({roomId,currentAnswer,apiKey}) => {

    return async (dispatch)=>{
      if(currentAnswer.trim()==''){
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: wrong_sentence });
      }
      else{
        try {
            dispatch({ type: STORY_MISSION_SEND});
            const solution={roomId,sentence:currentAnswer,apiKey};
            const res = await API.post(APIPaths.solveStory,solution);
            res
              ? checkStorySolveRespnose(res.data, dispatch)
              : dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
          } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
          }
        
      }
    }
  };

  const checkStorySolveRespnose= (data,dispatch) =>{
    const {reason,value} =data
    switch (reason) {
      case Wrong_Key:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: wrong_key_error }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: student_not_exist });
      case Success:
        alert(sentence_added)
        return dispatch({ type: CHANGE_STORY_IN_CHARGE, payload: {isInCharge:false,story:''} });
      default:
        console.log(data)
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
    }
  }

  export const sendFinishStory = ({roomId,apiKey}) =>{
    return async (dispatch)=>{
        try {
            dispatch({ type: STORY_MISSION_SEND});
            const solution={roomId,apiKey};
            const res = await API.post(APIPaths.finishStory,solution);
            res
              ? checkFinishStoryRespnose(res.data, dispatch,apiKey)
              : dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
          } catch (err) {
            console.log(err);
            return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
          }
    }
  }

  const checkFinishStoryRespnose= (data,dispatch,apiKey) =>{
    const {reason,value} =data
    switch (reason) {
      case Wrong_Key:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: wrong_key_error }); 
      case Not_Exist:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: student_not_exist });
      case Success:
        break;
      default:
        return dispatch({ type: UPDATE_ERROR_SOLVE_STORY, payload: server_error });
    }
  }