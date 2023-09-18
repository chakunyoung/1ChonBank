/* eslint-disable */
import { initializeApp } from 'firebase/app';
import { getMessaging, getToken } from 'firebase/messaging';
import apis from 'services/api/apis';
import { useSelector } from 'react-redux';

const firebaseConfig = {
    apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
    authDomain: "woowahan-bank.firebaseapp.com",
    projectId: "woowahan-bank",
    storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
    messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
    appId: process.env.REACT_APP_FIREBASE_APPID_KEY,
    measurementId: process.env.REACT_APP_MEASUREMENT_ID
  };

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

export const getFirebaseToken = async () => {
    try {
      const permission = await Notification.requestPermission();
      if (permission === 'granted') {
        const token = await getToken(messaging);
        console.log('FIREBASE - Token: ', token);
        return token;
      } else {
        console.log('FIREBASE - get token failed');
        return null;
      }
    } catch (err) {
      console.log('FIREBASE - Unable to get permission', err);
      return null;
    }
  };
  
  export const sendWebPushNotification = async (title, body) => {
    try {
      // 1. Firebase 토큰 얻기
      const token = await getFirebaseToken();
  
      if (token) {
        // 2. 백엔드에 웹 푸시 알림 요청 보내기
        const response = await apis.get('http://localhost:8080/api/notifications/web-push', {
          params: {
            firebaseToken: token,
            title,
            body,
          },
        });
  
        console.log('Web push notification sent successfully:', response.data);
      } else {
        console.log('Failed to get Firebase token');
      }
    } catch (error) {
      console.error('Error sending web push notification:', error);
    }
  };