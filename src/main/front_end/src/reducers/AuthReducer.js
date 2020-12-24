const initialState = {
  email: '',
  password: '',
};
import { EMAIL_CHANGED, PASSWORD_CHANGED } from '../actions/types';

export default (state = initialState, action) =>{
  switch (action.type) {
    case EMAIL_CHANGED:
      return { ...state, email: action.payload };
    case PASSWORD_CHANGED:
      return { ...state, password: action.payload };
    default:
      return state;
  }
};
