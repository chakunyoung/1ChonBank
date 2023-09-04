import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
// import store form './Redux/store';
// import store from './redux/store';
// import { PersistGate } from 'redux-persist/integration/react';
// import { persistStore } from 'redux-persist';

// import './Fonts/font.css';

// export const persistor = persistStore(store);

const rootElement = document.getElementById('root');
const root = ReactDOM.createRoot(rootElement);
// Redux의 상태를 컴포넌트에 제공하기 위해 Provider 컴포넌트 사용
root.render(

    <React.StrictMode>
      {/* <Provider store={store}> */}
      {/* <Provider> */}
        {/* <PersistGate loading={null} persistor={persistor}> */}
          <App />
        {/* </PersistGate> */}
      {/* </Provider> */}
    </React.StrictMode>

);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
