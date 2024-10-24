FROM openjdk:21-jdk

WORKDIR /app

COPY target/naves-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
