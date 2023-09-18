importScripts('https://www.gstatic.com/firebasejs/7.24.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.24.0/firebase-messaging.js');
importScripts('./FirebaseConfig.js'); // FirebaseConfig.js는 firebaseConfig 객체만 export 해야 합니다.

firebase.initializeApp(firebaseConfig);

// 이름 변경: messaging -> swMessaging
const swMessaging = firebase.messaging();

swMessaging.setBackgroundMessageHandler((payload) => {
  console.log('Received background message ', payload);

  const notificationTitle = 'Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    // icon: '/firebase-logo.png'
  };

  return self.registration.showNotification(notificationTitle, notificationOptions);
});
