package uk.co.idv.repository.dynamo;

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
        super("amazon/dynamodb-local:latest");
        withExposedPorts(8000);
        this.region = region;
    }

    public DynamoConfig getConfig() {
        final DynamoClientFactory factory = new DynamoClientFactory();
        factory.withEndpointConfiguration(getEndpointConfiguration());
        final AmazonDynamoDB client = factory.build();
        return new DynamoConfig(client);
    }

    public String getEndpointUri() {
        final String uri = String.format("http://%s:%s", getContainerIpAddress(), getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

    private EndpointConfiguration getEndpointConfiguration() {
        return new EndpointConfiguration(getEndpointUri(), region.getName());
    }

}