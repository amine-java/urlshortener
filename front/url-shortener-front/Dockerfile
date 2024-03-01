# Use Node.js LTS version as base image
FROM node:lts AS builder

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire project directory
COPY . .

# Build the Angular app
RUN npm run build

# Serve Angular application with Node.js Express
FROM node:14.17.0 AS server
WORKDIR /app
COPY --from=builder /app/dist/url-shortener-front ./dist
COPY server.js .

# Install Express
RUN npm install express


# Expose port 80
EXPOSE 80

# Command to run nginx
CMD ["node", "server.js"]
