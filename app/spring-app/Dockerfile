FROM adoptopenjdk/openjdk11:alpine-jre

ENV SPRING_PROFILES_ACTIVE=default
ENV SERVER_PORT=80

ENV AWS_REGION=eu-west-1
ENV ENVIRONMENT=dev

COPY build/libs/verification-context-spring-app-*.jar /opt/app.jar

CMD java -Djava.security.egd=file:/dev/./urandom -Daws.region=${AWS_REGION} -Denvironment=${ENVIRONMENT} -Daws.dynamo.db.endpoint.uri=${AWS_DYNAMO_DB_ENDPOINT_URI} -Xmx256m -jar /opt/app.jar