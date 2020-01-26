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

import java.time.Duration;

import static org.awaitility.Awaitility.await;

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
        withLogConsumer(outputFrame -> System.out.println(outputFrame.getUtf8String()));
        await().atMost(Duration.ofSeconds(10)).until(this::isRunning);
        final AWSCredentials credentials = new BasicAWSCredentials("abc", "123");
        final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final DynamoClientFactory factory = new DynamoClientFactory(credentialsProvider);
        final AmazonDynamoDB client = factory.withEndpointConfiguration(getEndpointConfiguration());
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
