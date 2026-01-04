FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar

RUN apk add --no-cache curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]