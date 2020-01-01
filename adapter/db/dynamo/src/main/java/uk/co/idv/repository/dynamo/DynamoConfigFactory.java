package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.repository.dynamo.DynamoConfig.DynamoConfigBuilder;

@Builder
@Slf4j
public class DynamoConfigFactory {

    public static DynamoConfig standard() {
        return AwsEnvironmentVariables.loadDynamoDbEndpointConfiguration()
                .map(DynamoConfigFactory::withEndpointConfiguration)
                .orElseGet(DynamoConfigFactory::withRegionOnly);
    }

    private static DynamoConfig withEndpointConfiguration(final EndpointConfiguration endpointConfiguration) {
        return dynamoConfigBuilder()
                .endpointConfiguration(endpointConfiguration)
                .build();
    }

    private static DynamoConfig withRegionOnly() {
        return dynamoConfigBuilder().build();
    }

    private static DynamoConfigBuilder dynamoConfigBuilder() {
        return DynamoConfig.builder()
                .environment(AwsEnvironmentVariables.loadEnvironment())
                .region(AwsEnvironmentVariables.loadRegion());
    }

}
