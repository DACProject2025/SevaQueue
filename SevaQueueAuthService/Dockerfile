# ============================================================
# Stage 1: BUILD STAGE
# ============================================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# ============================================================
# Stage 2: RUNTIME STAGE
# ============================================================
FROM eclipse-temurin:21-jre-alpine

# Security: run as non-root user
RUN addgroup -S sevaqueue && adduser -S sevaqueue -G sevaqueue

WORKDIR /app

# Copy the JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

RUN chown sevaqueue:sevaqueue app.jar

USER sevaqueue

# Auth service runs on default Spring Boot port 8080
EXPOSE 8080

# Health check using Spring Actuator endpoint
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
