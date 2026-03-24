# ============================================================
# Stage 1: BUILD STAGE - Node.js to build the React/Vite app
# ============================================================
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package files first (layer caching for npm install)
COPY package.json package-lock.json ./

# Install all dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the production bundle (outputs to /app/dist)
RUN npm run build

# ============================================================
# Stage 2: RUNTIME STAGE - Nginx serves static files
# Node image ~1GB → Nginx alpine ~25MB (HUGE difference!)
# ============================================================
FROM nginx:alpine

# Copy built assets from builder to nginx public directory
COPY --from=builder /app/dist /usr/share/nginx/html

# Copy our custom nginx config for React Router support
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# Nginx runs in foreground
CMD ["nginx", "-g", "daemon off;"]
