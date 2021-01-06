const initialState = {
  email: '',
  password: '',
  loading: false,
  errorMessage: '',
  teachersData: [],
  apiKey: '',
  userType: '',
  authCode: '',
};
import { IT, Student, Supervisor, Teacher } from '../actions/OpCodeTypes';
import {
  CLEAR_STATE,
  CODE_CHANGED,
  EMAIL_CHANGED,
  LOGIN_IT,
  LOGIN_STUDENT,
  LOGIN_SUPERVISOR,
  LOGIN_TEACHER,
  LOGIN_USER,
  PASSWORD_CHANGED,
  REGISTER_STUDENT,
  REGISTER_TEACHER,
  REGISTER_USER,
  UPDATE_ERROR,
  REGISTER_CODE,
  REGISTER_CODE_SUCCESS,
} from '../actions/types';

export default (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, password: action.payload };
    case CODE_CHANGED:
      return { ...state, authCode: action.payload };
    case REGISTER_USER:
      return { ...state, loading: true, errorMessage: '' };
    case UPDATE_ERROR:
      return { ...state, errorMessage: action.payload, loading: false };
    case REGISTER_STUDENT:
      return {
        ...initialState,
        teachersData: action.payload,
      };
    case REGISTER_TEACHER:
      return {
        ...initialState,
      };
    case REGISTER_CODE:
      return {
        ...state,
        loading: true,
        errorMessage: ''
      }
    case REGISTER_CODE_SUCCESS:
      return {
        ...initialState
      }
    case LOGIN_USER:
      return { ...state, loading: true, errorMessage: '' };
    case LOGIN_SUPERVISOR:
      return { ...initialState, apiKey: action.payload, userType: Supervisor };
    case LOGIN_IT:
      return { ...initialState, apiKey: action.payload, userType: IT };
    case LOGIN_TEACHER:
      return { ...initialState, apiKey: action.payload, userType: Teacher };
    case LOGIN_STUDENT:
      return { ...initialState, apiKey: action.payload, userType: Student };
    case CLEAR_STATE:
      return initialState;
    default:
      return state;
  }
};
