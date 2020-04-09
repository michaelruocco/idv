package uk.co.idv.dynamo.test;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class DynamoDbLocalContainer extends GenericContainer<DynamoDbLocalContainer> {

    private final Regions region;

    public DynamoDbLocalContainer() {
        this(Regions.EU_WEST_1);
    }

    public DynamoDbLocalContainer(final Regions region) {
        super("amazon/dynamodb-local:latest");
        withExposedPorts(8000);
        this.region = region;
    }

    public AmazonDynamoDB buildClient() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(buildCredentialsProvider())
                .withEndpointConfiguration(buildEndpointConfiguration())
                .build();
    }

    public String buildEndpointUri() {
        final String uri = String.format("http://%s:%s", getContainerIpAddress(), getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

    private EndpointConfiguration buildEndpointConfiguration() {
        return new EndpointConfiguration(buildEndpointUri(), region.getName());
    }

    private AWSCredentialsProvider buildCredentialsProvider() {
        final AWSCredentials credentials = new BasicAWSCredentials("abc", "123");
        return new AWSStaticCredentialsProvider(credentials);
    }

}
