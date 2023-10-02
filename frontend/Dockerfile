FROM node:16.13.0-alpine as builder

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY . /app

#nginx
FROM nginx:latest
RUN rm -rf /etc/nginx/conf.d
COPY conf /etc/nginx

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]
