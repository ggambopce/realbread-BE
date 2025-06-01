FROM openjdk:17-jdk-slim

WORKDIR /app

# jar 파일 복사
COPY build/libs/realbread-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
