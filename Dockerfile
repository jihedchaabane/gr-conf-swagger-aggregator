FROM openjdk:21
WORKDIR /var/lib/jenkins/workspace/jars
ADD target/gr-conf-swagger-aggregator-0.0.1-SNAPSHOT.jar gr-conf-swagger-aggregator.jar
COPY target/gr-conf-swagger-aggregator-0.0.1-SNAPSHOT.jar gr-conf-swagger-aggregator-0.0.1-SNAPSHOT.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "gr-conf-swagger-aggregator-0.0.1-SNAPSHOT.jar"]