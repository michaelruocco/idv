package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
public class AwsSystemProperties {

    private static final String REGION = "aws.region";
    private static final String DYNAMO_DB_ENDPOINT_URI = "aws.dynamo.db.endpoint.uri";
    private static final String ENVIRONMENT = "environment";

    private static final Regions DEFAULT_REGION = Regions.EU_WEST_1;

    private AwsSystemProperties() {
        // utility class
    }

    public static Regions loadRegion() {
        return loadRegionValue()
                .map(Regions::fromName)
                .orElseGet(AwsSystemProperties::loadDefaultRegion);
    }

    public static Optional<EndpointConfiguration> loadDynamoDbEndpointConfiguration() {
        return loadDynamoDbEndpointUri()
                .map(uri -> new EndpointConfiguration(uri, loadRegion().getName()));
    }

    public static String loadEnvironment() {
        return loadSystemProperty().orElse("dev");
    }

    private static Optional<String> loadRegionValue() {
        return loadSystemProperty(REGION);
    }

    private static Regions loadDefaultRegion() {
        log.info("{} system property not set returning default value {}", REGION, DEFAULT_REGION);
        return DEFAULT_REGION;
    }

    private static Optional<String> loadDynamoDbEndpointUri() {
        return loadSystemProperty(DYNAMO_DB_ENDPOINT_URI);
    }

    private static Optional<String> loadSystemProperty() {
        return loadSystemProperty(ENVIRONMENT);
    }

    private static Optional<String> loadSystemProperty(final String name) {
        final String value = System.getProperty(name);
        log.info("loaded system property {}={}", name, value);
        return Optional.ofNullable(value);
    }

}
