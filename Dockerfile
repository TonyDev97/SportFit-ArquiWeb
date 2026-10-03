FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiar archivos de Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Dar permisos y compilar
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Imagen final
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "app.jar"]