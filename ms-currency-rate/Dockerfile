FROM openjdk:17-jdk-slim
COPY build/libs/ms-currency-rate-1.0-SNAPSHOT.jar /app/
WORKDIR /app/
ENTRYPOINT ["java"]
CMD ["-jar", "/app/ms-currency-rate-1.0-SNAPSHOT.jar"]
