import API from "../api/API";
import * as APIPaths from '../api/APIPaths';
import { AddTriviaSubjectErrors, AddTriviaSubjectStrings, GeneralErrors } from '../locale/locale_heb';
import { DB_Error, Invalid_Trivia_Subject, Success, Trivia_Subject_Already_Exists, Wrong_Key } from "./OpCodeTypes";
import { RESET_ADD_TRIVIA_SUBJECT, TRIVIA_SUBJECT_ADDED, TRIVIA_SUBJECT_CHANGED, TRIVIA_SUBJECT_ERROR, TRIVIA_SUBJECT_PENDING } from "./types";

const {addedSubject} = AddTriviaSubjectStrings

const {
    alreadyExist,
    invalidSubject,
} = AddTriviaSubjectErrors

const {
    server_error,
} = GeneralErrors;

export const subjectChanged = (subject) => {
    return {
        type: TRIVIA_SUBJECT_CHANGED,
        payload: subject,
    }
}

export const addSubject = ({apiKey, subject}) =>{
    return async (dispatch) => {
        dispatch({type: TRIVIA_SUBJECT_PENDING})
        if (subject.trim() === ''){
            return dispatch({
                type: TRIVIA_SUBJECT_ERROR,
                payload: invalidSubject
            })
        }

        try{
            const res = await API.post(`${APIPaths.addTriviaSubject}?apiKey=${apiKey}`,{subject})
            res 
                ? checkAddTriviaSubjectResponse(res.data, dispatch)
                : dispatch({type: TRIVIA_SUBJECT_ERROR, payload: server_error})

        } catch(err){
            return dispatch({
                type: TRIVIA_SUBJECT_ERROR,
                payload: server_error
            })
        }

    }
}


const checkAddTriviaSubjectResponse = (data, dispatch) =>{
    const {reason} = data
    const generalError = {type: TRIVIA_SUBJECT_ERROR, payload: server_error}

    switch(reason){
        case Success:
            alert(addedSubject)
            return dispatch({type: TRIVIA_SUBJECT_ADDED})
        case Invalid_Trivia_Subject:
            return dispatch({type: TRIVIA_SUBJECT_ERROR, payload: invalidSubject})
        case Trivia_Subject_Already_Exists:
            return dispatch({type: TRIVIA_SUBJECT_ERROR, payload: alreadyExist})
        case Wrong_Key:
            return dispatch(generalError)
        case DB_Error:
            return dispatch(generalError)
        default:
            return dispatch(generalError)
    }
}

export const handleBack = ({navigation}) => {
    navigation.goBack();
    return {
        type: RESET_ADD_TRIVIA_SUBJECT
    }
}