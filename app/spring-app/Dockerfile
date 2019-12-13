FROM adoptopenjdk/openjdk11:alpine-jre

ENV SPRING_PROFILES_ACTIVE=stub
ENV SERVER_PORT=80
ENV AWS_REGION=eu-west-1

COPY build/libs/verification-context-spring-app-*.jar /opt/app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx256m", "-jar", "/opt/app.jar"]