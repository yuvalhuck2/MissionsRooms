const initialState = {
    search:'',
    profile:'',
    allUsers:[],
    presentedUsers:[],
    errorMessage: '',
    apiKey:'',
    firstName:'',
    lastName:'',
    enableFirstName:false,
    firstName:'',
    lastName:'',
    enableLastName:false,
    classroom:{},
    group:{},
    loading:false,
  };

import {
    SEARCH_CHANGED_MANAGE_USERS,
    MANAGED_USERS_CHANGED,
    RESET_MANAGE_USERS,
    PROFILE_CHANGED_MANAGE_USERS,
    FIRST_NAME_CHANGED_MANAGE_USERS,
    UPDATE_ERROR_MANAGE_USERS,
    WAIT_MANAGE_USERS,
    LAST_NAME_CHANGED_MANAGE_USERS,
    UPDATE_ALL_USER_PROFILES_MANAGE_USERS,
    ACTION_FINISHED_MANAGED_USERS,
    CHANGE_ENABLE_FIRST_NAME,
    CHANGE_ENABLE_LAST_NAME,
    LOGIN_IT,
    CLEAR_STATE,
    DELETE_USER,
} from '../actions/types';

export default (state = initialState, action) => {
    switch (action.type) {
        case SEARCH_CHANGED_MANAGE_USERS:
            return { ...state, search: action.payload };
        case FIRST_NAME_CHANGED_MANAGE_USERS:
            return { ...state, firstName: action.payload };
        case LAST_NAME_CHANGED_MANAGE_USERS:
            return { ...state, lastName: action.payload };
        case MANAGED_USERS_CHANGED:
            return {...state, presentedUsers: action.payload}
        case LOGIN_IT:
            return { ...initialState, apiKey: action.payload};
        case RESET_MANAGE_USERS:
            return { ...initialState, apiKey: action.payload};
        case PROFILE_CHANGED_MANAGE_USERS:
            return {...state, profile: action.payload}
        case WAIT_MANAGE_USERS:
            return {...state, loading:true}
        case ACTION_FINISHED_MANAGED_USERS:
            currentUser = state.presentedUsers.find((user)=> user.alias == state.profile)
            currentFromAllUsers = state.allUsers.find((user)=> user.alias == state.profile)
            if(action.payload.firstName.trim() != ''){
                currentUser.firstName = action.payload.firstName;
                currentFromAllUsers.firstName = action.payload.firstName;
            }
            if(action.payload.lastName.trim() != ''){
                currentUser.lastName = action.payload.lastName;
                currentFromAllUsers.lastName = action.payload.lastName;
            }
            
            return {...state, loading:false, firstName:'', lastName:'', errorMessage:''}
        case UPDATE_ALL_USER_PROFILES_MANAGE_USERS:
            return {... state, allUsers: action.payload, presentedUsers: action.payload}
        case CHANGE_ENABLE_FIRST_NAME:
            return {... state, enableFirstName:action.payload}
        case CHANGE_ENABLE_LAST_NAME:
            return {... state, enableLastName:action.payload}
        case UPDATE_ERROR_MANAGE_USERS:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case DELETE_USER:
            newAllUsers=state.allUsers.filter((user)=>user.alias!==action.payload.alias);
            newPresentedUsers=state.presentedUsers.filter((user)=>user.alias!==action.payload.alias)
            return {...state,allUsers:newAllUsers,presentedUsers:newPresentedUsers};

        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };