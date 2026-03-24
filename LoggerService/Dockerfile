# ============================================================
# Stage 1: BUILD STAGE - Use .NET 8 SDK for building
# ============================================================
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS builder

WORKDIR /app

# Copy the project file first for layer caching (NuGet restore)
COPY *.csproj .

# Restore NuGet packages (cached if .csproj unchanged)
RUN dotnet restore

# Copy everything else
COPY . .

# Publish the app in Release mode to /app/publish
RUN dotnet publish -c Release -o /app/publish --no-restore

# ============================================================
# Stage 2: RUNTIME STAGE - Use slim ASP.NET runtime image
# SDK image ~750MB → ASP.NET runtime ~200MB
# ============================================================
FROM mcr.microsoft.com/dotnet/aspnet:8.0

WORKDIR /app

# Copy published output from build stage
COPY --from=builder /app/publish .

# LoggerService runs on port 5000 (ASP.NET default)
EXPOSE 5000

# NOTE: LoggerService uses SQL Server. In Docker, we switch to
# a containerized SQL Server instead of LocalDB
ENV ASPNETCORE_ENVIRONMENT=Production
ENV ASPNETCORE_URLS=http://+:5000

ENTRYPOINT ["dotnet", "LoggerService.dll"]
