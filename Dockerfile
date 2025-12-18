FROM eclipse-temurin:17-jdk
LABEL authors="boukn"

WORKDIR /app

# Copier le jar compilé
COPY target/*.jar app.jar

# Exposer le port
EXPOSE 8080

# Lancer Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
