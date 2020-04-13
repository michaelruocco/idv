FROM adoptopenjdk/openjdk13:alpine-jre

ENV SPRING_PROFILES_ACTIVE=default
ENV SERVER_PORT=80

ENV AWS_REGION=eu-west-1
ENV ENVIRONMENT=dev
ENV STUBBED_PHONE_NUMBER=07809386681

COPY build/libs/idv-spring-app-*.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Daws.region=${AWS_REGION} \
  -Denvironment=${ENVIRONMENT} \
  -Daws.dynamo.db.endpoint.uri=${AWS_DYNAMO_DB_ENDPOINT_URI} \
  -Daws.sns.endpoint.uri=${AWS_SNS_ENDPOINT_URI} \
  -Dstubbed.phone.number=${STUBBED_PHONE_NUMBER} \
  -jar /opt/app.jar