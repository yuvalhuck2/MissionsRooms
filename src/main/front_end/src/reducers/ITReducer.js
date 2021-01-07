const initialState = {text: '', files: []};
import {PICKED_FILE, RESET_FILES} from '../actions/types'

export default (state = initialState, action) => {
    switch(action.type) {
        case PICKED_FILE:
            return {...state, files: [...state.files, action.payload], text: state.text + " " + action.payload.name}
        case RESET_FILES:
            return {...state, files: [], text: ""}
        default:
            return state
    }
} 
