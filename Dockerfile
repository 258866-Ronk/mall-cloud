FROM maven:3.9.9-eclipse-temurin-17 AS build
ARG SERVICE
WORKDIR /workspace
COPY . .
RUN mvn -ntp -pl ${SERVICE} -am -DskipTests package

FROM eclipse-temurin:17-jre
ARG SERVICE
WORKDIR /app
COPY --from=build /workspace/${SERVICE}/target/${SERVICE}-1.0.0-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
