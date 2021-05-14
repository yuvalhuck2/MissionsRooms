const initialState = {
    apiKey: "",
    errorMessage: "",
    loading: false,
    question: "",
    wrongAnswers: ["", "", ""],
    correctAnswer: "",
    questionSubject: "",
    subjects: [],
}

import {
    TRIVIA_QUESTION_CHANGED,
    TRIVIA_SUBJECT_CHANGED
} from '../actions/types';

export default (state = initialState, action) => {
    switch(action.type){
        case TRIVIA_QUESTION_CHANGED:
            return {...state, question: action.payload}
        case TRIVIA_SUBJECT_CHANGED:
            return {...state, questionSubject: action.payload}
        default:
            return state;
    }
}