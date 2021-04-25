import {
    TRIVIA_SUBJECT_CHANGED,
    TRIVIA_SUBJECT_ERROR,
    TRIVIA_SUBJECT_ADDED,
    LOGIN_IT,
    LOGIN_TEACHER,
    RESET_ADD_TRIVIA_SUBJECT,
    TRIVIA_SUBJECT_PENDING
} from "../actions/types"

const initialState = {
    subject: "",
    errorMessage: "",
    apiKey: "",
    loading: false
}

export default (state = initialState, action) => {
    switch(action.type){
        case LOGIN_IT:
            return {...state, apiKey: action.payload}
        case LOGIN_TEACHER:
            return {...state, apiKey: action.payload}
        case TRIVIA_SUBJECT_CHANGED:
            return {...state, subject: action.payload}
        case TRIVIA_SUBJECT_ERROR:
            return {...state, errorMessage: action.payload, loading: false}
        case TRIVIA_SUBJECT_PENDING:
            return {...state, errorMessage: "", loading: true}
        case TRIVIA_SUBJECT_ADDED:
            return {...state, errorMessage: "", loading: false, subject: ""}
        case RESET_ADD_TRIVIA_SUBJECT:
            return {...state, errorMessage: "", loading: false, subject: ""}
        default:
            return state
    }
}

