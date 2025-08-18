# ---- build stage ----
FROM maven:3.9.11-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ---- runtime stage ----
FROM amazoncorretto:21-alpine
WORKDIR /app
RUN adduser --system --home /app --shell /sbin/nologin appuser
COPY --from=build /app/target/*.jar /app/app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -Dfile.encoding=UTF-8"
EXPOSE 8080
USER appuser
ENTRYPOINT ["java","-jar","/app/app.jar"]