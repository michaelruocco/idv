package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@RequiredArgsConstructor
public class DynamoClientFactory {

    private final Regions region;

    public DynamoClientFactory() {
        this(AwsSystemProperties.loadRegion());
    }

    public AmazonDynamoDBClientBuilder standard() {
        final AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        final Optional<EndpointConfiguration> endpointConfig = AwsSystemProperties.loadDynamoDbEndpointConfiguration();
        if (endpointConfig.isPresent()) {
            builder.withEndpointConfiguration(endpointConfig.get());
        } else {
            builder.withRegion(region);
        }
        return builder;
    }

    public AmazonDynamoDBClientBuilder withCredentialsProvider(final AWSCredentialsProvider credentialsProvider) {
        return standard().withCredentials(credentialsProvider);
    }

}
