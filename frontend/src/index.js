import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import store from 'redux/store';

// import store form './Redux/store';
// import store from './redux/store';
// import { PersistGate } from 'redux-persist/integration/react';
// import { persistStore } from 'redux-persist';
// import './Fonts/font.css';
// export const persistor = persistStore(store);

const rootElement = document.getElementById('root');
const root = ReactDOM.createRoot(rootElement);
root.render(
    // <React.StrictMode> // 필요없어서 제거함
      <Provider store={store}> {/* Provider를 사용하여 store를 연결합니다 */}
      <App />
    </Provider>
    // </React.StrictMode>
);
reportWebVitals();
