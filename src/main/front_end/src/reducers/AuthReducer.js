const initialState = {
  email: '',
  password: '',
  loading: false,
};
import {
  EMAIL_CHANGED,
  PASSWORD_CHANGED,
  REGISTER_USER,
} from '../actions/types';

export default (state = initialState, action) => {
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, password: action.payload };
    case REGISTER_USER:
      return { ...state, loading: true };
    default:
      return state;
  }
};
