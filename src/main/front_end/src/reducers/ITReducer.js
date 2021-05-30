const initialState = { text: '', files: [], errorMessage: '', successMessage: '', apiKey: '' };
import { PICKED_FILE, RESET_FILES, CLEAR_STATE, UPDATE_ERROR_IT, UPLOAD_CSV_SUCCESS, LOGIN_IT, DEFAULT, RESET_IT } from '../actions/types'

export default (state = initialState, action) => {
    switch (action.type) {
        case PICKED_FILE:
            return { ...state, files: [...state.files, action.payload], text: state.text + " " + action.payload.name, errorMessage: '', successMessage: '' }
        case RESET_FILES:
            return { ...state, files: [], text: "", errorMessage: '', successMessage: '' }
        case CLEAR_STATE:
            return initialState;
        case UPDATE_ERROR_IT:
            return { ...state, errorMessage: action.payload, successMessage: '' }
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload }
        case UPLOAD_CSV_SUCCESS:
            return { ...state, errorMessage: '', successMessage: action.payload }
        case RESET_IT:
            return { ...initialState, apiKey: action.payload };
        default:
            return state
    }
}
