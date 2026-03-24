# ============================================================
# Stage 1: BUILD STAGE
# We use a full Maven + JDK image to compile and package the app
# ============================================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set the working directory INSIDE the container
WORKDIR /app

# Copy ONLY pom.xml first (for Docker layer caching)
# If source code changes but pom.xml doesn't, Maven deps won't re-download
COPY pom.xml .

# Download all dependencies (cached layer if pom.xml unchanged)
RUN mvn dependency:go-offline -B

# Now copy the full source code
COPY src ./src

# Build the JAR, skipping tests for faster build
RUN mvn clean package -DskipTests

# ============================================================
# Stage 2: RUNTIME STAGE
# Use a slim JRE-only image (much smaller than JDK!)
# JDK image ~600MB → JRE slim image ~200MB
# ============================================================
FROM eclipse-temurin:21-jre-alpine

# Create a non-root user for security best practice
RUN addgroup -S sevaqueue && adduser -S sevaqueue -G sevaqueue

WORKDIR /app

# Copy only the built JAR from the builder stage (multi-stage magic!)
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to our non-root user
RUN chown sevaqueue:sevaqueue app.jar

# Switch to non-root user
USER sevaqueue

# Expose the port the app runs on
EXPOSE 8081

# Health check - Docker will mark container unhealthy if this fails
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8081/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
