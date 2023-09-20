import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import store, { persistor } from 'redux/store'; // 수정된 import 문

const rootElement = document.getElementById('root');
const root = ReactDOM.createRoot(rootElement);
ReactDOM.render(
  <Provider store={store}> {/* Provider를 사용하여 store를 연결합니다 */}
    <PersistGate loading={<div>Loading...</div>} persistor={persistor}>
      {/* local storage persist */}
      <App />
    </PersistGate>
  </Provider>,
  rootElement
);
reportWebVitals();

if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/firebase-messaging-sw.js')
      .then(registration => {
        console.log('ServiceWorker registration successful with scope: ', registration.scope);
      })
      .catch(error => {
        console.log('ServiceWorker registration failed: ', error);
      });
  });
}

