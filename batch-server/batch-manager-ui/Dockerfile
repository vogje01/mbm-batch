FROM node:latest as build

# Create app directory
WORKDIR /app/batch/batch-manager-ui

# Add `/app/node_modules/.bin` to $PATH
ENV PATH /app/batch/batch-manager-ui/node_modules/.bin:$PATH

# Build application
COPY package.json /app/batch/batch-manager-ui/package.json
#COPY webpack.config.js /app/batch/batch-manager-ui/
COPY .env.production /app/batch/batch-manager-ui/
RUN npm install --silent
RUN npm audit fix
RUN npm install react-scripts@3.4.1 -g --silent
ADD ./src /app/batch/batch-manager-ui/src
ADD ./public /app/batch/batch-manager-ui/public
RUN npm run build:production

# Server
FROM nginx:latest
COPY --from=build /app/batch/batch-manager-ui/build /usr/share/nginx/html
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx/nginx.conf /etc/nginx/conf.d
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]