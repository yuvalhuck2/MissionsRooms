const initialState = {
    alias: '',
    firstName: '',
    lastName: '',
    grade: '',
    classNumber: '',
    classGroup: 'A',
    isStudent: true,
    isSupervisor: false,
    loading: false,
    errorMessage: '',
    apiKey: '',
  };

  import {
    CLEAR_STATE,
    ALIAS_USER_CHANGED,
    FIRSTNAME_USER_CHANGED,
    LASTNAME_USER_CHANGED,
    GRADE_USER_CHANGED,
    CLASS_NUMBER_CHANGED,
    CLASSGORUP_USER_CHANGED,
    SUPERVISOR_USER_CHANGED,
    LOGIN_IT,
    UPDATE_ADD_USER_ERROR,
    ADD_USER_RESET,
    IS_STUDENT_USER_CHANGED,
    WAIT_FOR_ADD_USER,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
      case ALIAS_USER_CHANGED:
        return { ...state, alias: action.payload };
      case FIRSTNAME_USER_CHANGED:
        return { ...state, firstName: action.payload };
      case LASTNAME_USER_CHANGED:
        return { ...state, lastName: action.payload };
      case GRADE_USER_CHANGED:
        return { ...state, grade: action.payload };
      case CLASS_NUMBER_CHANGED:
        return { ...state, classNumber: action.payload };
      case CLASSGORUP_USER_CHANGED:
        return { ...state, classGroup: action.payload };
      case IS_STUDENT_USER_CHANGED:
        return { ...initialState, apiKey: state.apiKey, isStudent: action.payload };
      case SUPERVISOR_USER_CHANGED:
        return { ...state, isSupervisor: !state.isSupervisor };
      case UPDATE_ADD_USER_ERROR:
          alert(action.payload)
        return { ...state, errorMessage: action.payload, loading: false };
      case WAIT_FOR_ADD_USER:
        return {
          ...state,
          loading: true,
          errorMessage: '',
        };
      case LOGIN_IT:
        return { ...initialState, apiKey: action.payload};
      case ADD_USER_RESET:
        return {... initialState, apiKey: state.apiKey, isStudent: state.isStudent}
      case CLEAR_STATE:
        return initialState;
      default:
        return state;
    }
  };