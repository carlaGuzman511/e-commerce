FROM node:14.21.3 AS builder

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .

RUN npm run build -- --output-path /usr/share/nginx/html --prod

FROM nginx:alpine AS runner

COPY nginx-docker.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /usr/share/nginx/html /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
