FROM node:20.11.1-alpine AS builder
WORKDIR /reddit-frontend-app
COPY . .
RUN npm i
RUN npm run build --prod

FROM nginx:stable-alpine
COPY --from=builder /reddit-frontend-app/dist/reddit-clone-frontend/ /usr/share/nginx/html