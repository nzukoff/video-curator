import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import promiseMiddleware from 'redux-promise-middleware';
import thunkMiddleware from 'redux-thunk';
import asyncAwait from 'redux-async-await';
import App from './App';
import SubmitVideo from './components/SubmitVideo/SubmitVideo';
import vcApp from './reducers'
import './index.css';

const store = createStore(
  vcApp,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(), 
  applyMiddleware(promiseMiddleware())
);

// const store = applyMiddleware(asyncAwait)(createStore)(vcApp,
//   window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());


ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);

export default store;