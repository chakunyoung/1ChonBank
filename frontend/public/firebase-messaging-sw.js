importScripts('https://www.gstatic.com/firebasejs/7.24.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/7.24.0/firebase-messaging.js');
importScripts('./FirebaseConfig.js');

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler((payload) => {
    console.log('Received background message ', payload);  // 로그 추가
    const {title, body} = payload.notification;
    const {clickAction, icon} = payload.data;

    const notificationOptions = {
        body: body || 'Background Message body.',
        icon: icon || '/char5x4.png',
        data: {clickAction: clickAction || 'http://localhost:3000/mypage'},
    };

    return self.registration.showNotification(title || 'Default Title', notificationOptions);
});

self.addEventListener('notificationclick', function (event) {
    console.log('On notification click: ', event.notification.tag);  // 로그 추가

    const urlToOpen = new URL(event.notification.data.clickAction, self.location.origin).href;

    const promiseChain = clients.matchAll({
        type: 'window',
        includeUncontrolled: true
    })
        .then((windowClients) => {
            let matchingClient = null;

            for (let i = 0; i < windowClients.length; i++) {
                const windowClient = windowClients[i];
                if (windowClient.url === urlToOpen) {
                    matchingClient = windowClient;
                    break;
                }
            }

            if (matchingClient) {
                return matchingClient.focus();
            } else {
                return clients.openWindow(urlToOpen);
            }
        });

    event.waitUntil(promiseChain);
});
