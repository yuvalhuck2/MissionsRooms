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
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  REGISTER_STUDENT,
  REGISTER_TEACHER,
  REGISTER_USER,
  LOGIN_USER,
  LOGIN_TEACHER,
  LOGIN_STUDENT,
  LOGIN_IT,
  LOGIN_SUPERVISOR,
  UPDATE_ERROR,
  CODE_CHANGED,
} from '../actions/types';

export default (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, password: action.payload };
    case CODE_CHANGED:
      console.log(action.payload)
      return {...state, authCode: action.payload}
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
