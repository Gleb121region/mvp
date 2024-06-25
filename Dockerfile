FROM openjdk:21-slim AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/target/mvp-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "mvp-0.0.1-SNAPSHOT.jar"]