FROM openjdk:11-jre-slim
EXPOSE 8080
VOLUME /RoomManagement
ARG JAR_FILE=./build/libs/manage-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} manage.jar
ENTRYPOINT ["java","-jar","/manage.jar"]
LABEL maintainer="rlaxowns7916@gmail.com"
