FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/usemongodocker-1.0-SNAPSHOT.jar user.jar
RUN /bin/sh -c 'touch /user.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/test","-Djava.security.egd=file:/dev/./urandom","-jar","/user.jar"]