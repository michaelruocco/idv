package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.RequiredArgsConstructor;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@RequiredArgsConstructor
public class DynamoClientFactory {

    private final Regions region;
    private final AWSCredentialsProvider credentialsProvider;

    public DynamoClientFactory() {
        this(AwsEnvironmentVariables.loadRegion(), new DefaultAWSCredentialsProviderChain());
    }

    public AmazonDynamoDB build() {
        return AwsEnvironmentVariables.loadDynamoDbEndpointConfiguration()
                .map(this::withEndpointConfiguration)
                .orElse(standard());
    }

    public AmazonDynamoDB standard() {
        return builder().withRegion(region)
                .build();
    }

    public AmazonDynamoDB withEndpointConfiguration(final EndpointConfiguration endpointConfiguration) {
        return builder().withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    private AmazonDynamoDBClientBuilder builder() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider);
    }

}
