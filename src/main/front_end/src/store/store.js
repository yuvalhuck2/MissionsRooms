import { applyMiddleware, createStore } from 'redux';
import thunk from 'redux-thunk';
import AuthReducer from '../reducers';

const initialState = {};

const middleware = [thunk];

store = createStore(AuthReducer, initialState, applyMiddleware(...middleware));

export default store;
