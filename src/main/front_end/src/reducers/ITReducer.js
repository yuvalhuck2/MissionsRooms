const initialState = {text: '', files: [], apiKey:''};
import {PICKED_FILE, RESET_FILES, CLEAR_STATE} from '../actions/types'

export default (state = initialState, action) => {
    switch(action.type) {
        case PICKED_FILE:
            return {...state, files: [...state.files, action.payload], text: state.text + " " + action.payload.name}
        case RESET_FILES:
            return {...state, files: [], text: ""}
        case CLEAR_STATE:
            return initialState;
        default:
            return state
    }
} 
