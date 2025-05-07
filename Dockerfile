FROM mirror.gcr.io/library/openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
RUN mkdir -p src/main/resources/tables
COPY src/main/resources/tables/table.xlsx src/main/resources/tables
COPY src/main/resources/tables/table1.xlsx src/main/resources/tables
VOLUME src/main/resources/tables
ENTRYPOINT ["java","-jar","/app.jar"]
