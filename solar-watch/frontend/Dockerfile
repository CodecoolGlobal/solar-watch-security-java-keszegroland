#Stage 1
FROM node:18-alpine AS builder

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .

RUN npm run build

# Stage 2
FROM nginx:alpine AS runner

COPY nginx.conf /etc/nginx/conf.d/nginx.conf

COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 3111

CMD ["nginx", "-g", "daemon off;"]