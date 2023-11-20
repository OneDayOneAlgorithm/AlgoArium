FROM openjdk:17-jre
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} algoarium-0.0.1-SNAPSHOT.jar
EXPOSE 8090
#ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["java", "-jar", "algoarium-0.0.1-SNAPSHOT.jar"]



#
#            FROM adoptopenjdk/openjdk11
#            WORKDIR /usr/app
#            COPY target/style-0.0.1-SNAPSHOT.jar stylewithus-server.jar
#            EXPOSE 8082
#            CMD ["java", "-jar", "stylewithus-server.jar"]
#