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
    enableLastName:false,
    classroom:{},
    group:{},
    loading:false,
    allClassrooms:[],
    currUser:'',
    chosenClassroom:'',
    chosenGroup:'',
    visible:true,
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
    RESET_MANAGE_USERS_TRANSFER,
    DELETE_USER,
    UPDATE_ERROR_DELETE_USER,
    PASS_TO_TRANSFER_TEACHER,
    TRANSFER_GROUP_CHANGED,
    TRANSFER_CLASSROOM_CHANGED,
    TRANSFER_TEACHER_SUCCESS,
    UPDATE_ERROR_TRANSFER_TEACHER,
    PASS_TO_TRANSFER_STUDENT, TRANSFER_STUDENT_SUCCESS,UPDATE_ERROR_TRANSFER_STUDENT
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
        case RESET_MANAGE_USERS_TRANSFER:
            return {...state,visible:true,profile:'',firstName:'',lastName:'',chosenClassroom:'',chosenGroup:''}
        case PROFILE_CHANGED_MANAGE_USERS:
            currentUser = state.presentedUsers.find((user)=> user.alias == action.payload)
            return {...state,profile: action.payload,currUser:currentUser}
        case WAIT_MANAGE_USERS:
            return {...state, loading:true}
        case ACTION_FINISHED_MANAGED_USERS:
            console.log(action.payload)
            currentUser = state.presentedUsers.find((user)=> user.alias == state.profile)
            currentFromAllUsers = state.allUsers.find((user)=> user.alias == state.profile)
            if(action.payload.firstName.trim() != ''){
                currentUser.firstName = action.payload.firstName;
                currentFromAllUsers.firstName = action.payload.firstName;
            }
            if(action.payload.lastName.trim() != ''){
                console.log("herrrreee")
                currentUser.lastName = action.payload.lastName;
                currentFromAllUsers.lastName = action.payload.lastName;
            }
            
            return {...state, loading:false, firstName:'', lastName:'', errorMessage:'',currUser:''}
        case PASS_TO_TRANSFER_TEACHER:
            return {...state,allClassrooms:action.payload.classrooms,visible:false};
        case PASS_TO_TRANSFER_STUDENT:
            return {...state,allClassrooms:action.payload.classrooms,visible:false};
        case UPDATE_ALL_USER_PROFILES_MANAGE_USERS:
            return {... state, allUsers: action.payload, presentedUsers: action.payload}
        case CHANGE_ENABLE_FIRST_NAME:
            return {... state, enableFirstName:action.payload}
        case CHANGE_ENABLE_LAST_NAME:
            return {... state, enableLastName:action.payload}
        case UPDATE_ERROR_MANAGE_USERS:
            return { ...state, errorMessage: action.payload, loading:false};
        case DELETE_USER:
            newAllUsers=state.allUsers.filter((user)=>user.alias!==action.payload.alias);
            newPresentedUsers=state.presentedUsers.filter((user)=>user.alias!==action.payload.alias)
            return {...state,allUsers:newAllUsers,presentedUsers:newPresentedUsers};
        case TRANSFER_GROUP_CHANGED:
            return {...state,chosenGroup: action.payload};
        case TRANSFER_CLASSROOM_CHANGED:
            return{...state,chosenClassroom: action.payload}
        case TRANSFER_TEACHER_SUCCESS:
            return{...state,profile:'',visible:true,chosenClassroom:'',chosenGroup:''};
        case TRANSFER_STUDENT_SUCCESS:
            return{...state,profile:'',visible:true,chosenClassroom:'',chosenGroup:''};
        case UPDATE_ERROR_TRANSFER_TEACHER:
            return{...state,profile:'',visible:true};
        case UPDATE_ERROR_TRANSFER_STUDENT:
            return{...state,profile:'',visible:true};
        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };