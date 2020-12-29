import API from '../api/API';
import { EMAIL_CHANGED, PASSWORD_CHANGED, REGISTER_USER } from './types';

// const { manifest } = Constants;

// const uri = `http://${manifest.debuggerHost.split(':').shift()}:8080`;

// console.log(uri);

export const emailChanged = (text) => {
  return {
    type: EMAIL_CHANGED,
    payload: text,
  };
};

export const passwordChanged = (text) => {
  return {
    type: PASSWORD_CHANGED,
    payload: text,
  };
};

export const registerUser = ({ email, password }) => {
  return async (dispatch) => {
    dispatch({ type: REGISTER_USER });
    console.log(email, password);
    const res = await API.post('/UserAuth', { email, password });
    console.log(res.data);
  };
};
