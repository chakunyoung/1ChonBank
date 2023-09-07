import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { store, persistor } from 'redux/store'; // 수정된 import 문

const rootElement = document.getElementById('root');
const root = ReactDOM.createRoot(rootElement);
root.render(
  <Provider store={store}> {/* Provider를 사용하여 store를 연결합니다 */}
    <PersistGate loading={null} persistor={persistor}> {/* local storage persist */}
      <App />
    </PersistGate>
  </Provider>
);
reportWebVitals();
