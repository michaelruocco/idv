FROM adoptopenjdk/openjdk11:alpine-jre

ENV SPRING_PROFILES_ACTIVE=stub
USER 10001

COPY build/libs/verification-context-spring-app-*.jar /opt/app.jar
EXPOSE 8080

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Xmx256m", "-jar", "/opt/app.jar"]