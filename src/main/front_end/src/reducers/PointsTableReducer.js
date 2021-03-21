const initialState = {
    points: 0,
    tab:'Personal',
    students:[],
    groups:[],
    classrooms: [],
    current: [],
    canReduceToAll:false,
    apiKey:'',
    errorMessage:'',
    loading:false,
    isStudent: true,
  };

  import {
    POINTS_TO_REDUCE_CHANGED,
    POINTS_TAB_CHANGED,
    LOGIN_STUDENT,
    LOGIN_TEACHER,
    INIT_POINTS_TABLE,
    RESET_POINTS_TABLE,
    WAIT_FOR_REDUCE,
    UPDATE_ERROR_POINTS_TABLE,
    REDUCE_COMPLETED,
    CLEAR_STATE,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
        case POINTS_TO_REDUCE_CHANGED:
            return { ...state, points: action.payload };
        case POINTS_TAB_CHANGED:
            return { ...state, tab: action.payload.tab, current:action.payload.list };
        case INIT_POINTS_TABLE:
            return {...state, students: action.payload.studentsPointsData, groups: action.payload.groupsPointsData,
            classrooms: action.payload.classroomsPointsData, canReduceToAll: action.payload.isSupervisor,
            current: action.payload.studentsPointsData, isStudent: action.payload.isStudent}
        case LOGIN_STUDENT:
        case LOGIN_TEACHER:
            return { ...initialState, apiKey: action.payload};
        case RESET_POINTS_TABLE:
            return { ...initialState, apiKey: action.payload};
        case WAIT_FOR_REDUCE:
            return {...state, loading:true}
        case REDUCE_COMPLETED:
            return {...state, loading:false}
        case UPDATE_ERROR_POINTS_TABLE:
            alert(action.payload)
            return { ...state, errorMessage: action.payload, loading:false};
        case CLEAR_STATE:
            return initialState
        default:
            return state;
    }
  };