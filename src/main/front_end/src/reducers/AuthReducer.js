const initialState = {
  email: '',
  password: '',
  loading: false,
  errorMessage: '',
  is_student: false,
  teachers_data: [],
};
import {
  CLEAR_STATE,
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  REGISTER_STUDENT,
  REGISTER_TEACHER,
  REGISTER_USER,
  UPDATE_ERROR,
} from '../actions/types';

export default (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, password: action.payload };
    case REGISTER_USER:
      return { ...state, loading: true, errorMessage: '' };
    case UPDATE_ERROR:
      return { ...state, errorMessage: action.payload, loading: false };
    case REGISTER_STUDENT:
      return {
        ...state,
        is_student: true,
        teachers_data: action.payload,
        loading: false,
        errorMessage: false,
      };
    case REGISTER_TEACHER:
      return {
        ...state,
        is_student: false,
        teachers_data: [],
        loading: false,
        errorMessage: false,
      };
    case CLEAR_STATE:
      return initialState;
    default:
      return state;
  }
};
