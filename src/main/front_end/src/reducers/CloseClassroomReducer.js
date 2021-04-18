const initialState = {
    grade: '',
    classNumber: '',
    classGroup: 'A',
    loading: false,
    errorMessage: '',
    apiKey: '',
  };

  import {
    CLEAR_STATE,
    GRADE_CLASSROOM_CHANGED,
    CLASS_NUMBER_CLASSROOM_CHANGED,
    LOGIN_IT,
    UPDATE_CLOSE_CLASSROOM_ERROR,
    CLOSE_CLASSROOM_RESET,
    WAIT_FOR_CLOSE_CLASSROOM,
  } from '../actions/types';
  
  export default (state = initialState, action) => {
    switch (action.type) {
      case GRADE_CLASSROOM_CHANGED:
        return { ...state, grade: action.payload };
      case CLASS_NUMBER_CLASSROOM_CHANGED:
        return { ...state, classNumber: action.payload };
      case UPDATE_CLOSE_CLASSROOM_ERROR:
          alert(action.payload)
        return { ...state, errorMessage: action.payload, loading: false };
      case WAIT_FOR_CLOSE_CLASSROOM:
        return {
          ...state,
          loading: true,
          errorMessage: '',
        };
      case LOGIN_IT:
        return { ...initialState, apiKey: action.payload};
      case CLOSE_CLASSROOM_RESET:
        return {... initialState, apiKey: state.apiKey}
      case CLEAR_STATE:
        return initialState;
      default:
        return state;
    }
  };