# Nginx 알파인 버전을 베이스 이미지로 사용
FROM nginx:alpine

# 빌드된 React 앱을 Nginx가 서빙할 수 있도록 설정
COPY build/ /usr/share/nginx/html

# Nginx 설정 파일을 복사 (필요한 경우)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 80번 포트를 열어 외부와 통신하게 합니다.
EXPOSE 80

# Nginx 서버를 실행
CMD ["nginx", "-g", "daemon off;"]