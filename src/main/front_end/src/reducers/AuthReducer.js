const initialState = {
  email: '',
  password: '',
  loading: false,
  errorMessage: '',
};
import {
  CLEAR_STATE,
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
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
      return { ...state, loading: true };
    case UPDATE_ERROR:
      console.log(`payload is:${action.payload}`)
      return { ...state, errorMessage: action.payload, loading: false };
    case CLEAR_STATE:
      return initialState;
    default:
      return state;
  }
};
