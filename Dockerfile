FROM gradle:8.4-jdk17

WORKDIR /opt/app

COPY . .

RUN gradle build

FROM eclipse-temurin:17-jre-jammy

WORKDIR /opt/app

COPY --from=0 /opt/app/build/libs/covid-api-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-jar", "/app.jar"]