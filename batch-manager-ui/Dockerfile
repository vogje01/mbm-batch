FROM node:latest as build

# Create app directory
WORKDIR /app/fis-batch/fis-batch-manager-ui

# Add `/app/node_modules/.bin` to $PATH
ENV PATH /app/fis-batch/fis-batch-manager-ui/node_modules/.bin:$PATH

# Build application
COPY package.json /app/fis-batch/fis-batch-manager-ui/package.json
COPY webpack.config.js /app/fis-batch/fis-batch-manager-ui/
COPY .env.production /app/fis-batch/fis-batch-manager-ui/
RUN npm install --silent
RUN npm install react-scripts@3.1.1 -g --silent
ADD ./src /app/fis-batch/fis-batch-manager-ui/src
ADD ./public /app/fis-batch/fis-batch-manager-ui/public
RUN npm run build:production

# Server
FROM nginx:latest
COPY --from=build /app/fis-batch/fis-batch-manager-ui/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]