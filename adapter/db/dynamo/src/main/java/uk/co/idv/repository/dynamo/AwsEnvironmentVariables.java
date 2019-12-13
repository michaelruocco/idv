package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
public class AwsEnvironmentVariables {

    private static final String REGION = "AWS_REGION";
    private static final String DYNAMO_DB_URI = "DYNAMO_DB_URI";

    private static final Regions DEFAULT_REGION = Regions.EU_WEST_1;

    private AwsEnvironmentVariables() {
        // utility class
    }

    public static Regions loadRegion() {
        return loadRegionValue()
                .map(Regions::fromName)
                .orElseGet(AwsEnvironmentVariables::loadDefaultRegion);
    }

    public static Optional<EndpointConfiguration> loadDynamoDbEndpointConfiguration() {
        return loadDynamoDbEndpointUri().map(uri -> new EndpointConfiguration(uri, loadRegion().getName()));
    }

    private static Optional<String> loadRegionValue() {
        return loadEnvironmentVariable(REGION);
    }

    private static Regions loadDefaultRegion() {
        log.info("{} environment variable not set returning default value {}", REGION, DEFAULT_REGION);
        return DEFAULT_REGION;
    }

    private static Optional<String> loadDynamoDbEndpointUri() {
        return loadEnvironmentVariable(DYNAMO_DB_URI);
    }

    private static Optional<String> loadEnvironmentVariable(final String name) {
        final String value = System.getenv(name);
        log.info("loaded environment variable {}={}", name, value);
        return Optional.ofNullable(value);
    }

}
