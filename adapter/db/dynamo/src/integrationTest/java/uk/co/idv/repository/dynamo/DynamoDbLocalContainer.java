package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class DynamoDbLocalContainer extends GenericContainer<DynamoDbLocalContainer> {

    private final Regions region;

    public DynamoDbLocalContainer() {
        this(Regions.EU_WEST_1);
    }

    public DynamoDbLocalContainer(final Regions region) {
        super("localstack/localstack:latest");
        withExposedPorts(4569);
        this.region = region;
    }

    public DynamoConfig buildConfig() {
        final AWSCredentials credentials = new BasicAWSCredentials("abc", "123");
        final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final DynamoClientFactory factory = new DynamoClientFactory(credentialsProvider);
        final AmazonDynamoDB client = factory.withEndpointConfiguration(buildEndpointConfiguration());
        return new DynamoConfig(client);
    }

    private EndpointConfiguration buildEndpointConfiguration() {
        return new EndpointConfiguration(buildEndpointUri(), region.getName());
    }

    private String buildEndpointUri() {
        final String uri = String.format("http://%s:%s", getContainerIpAddress(), getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

}
