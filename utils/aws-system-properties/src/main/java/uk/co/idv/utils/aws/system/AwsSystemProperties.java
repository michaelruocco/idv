package uk.co.idv.utils.aws.system;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class AwsSystemProperties {

    private static final String REGION = "aws.region";
    private static final String ENVIRONMENT = "environment";

    private static final Regions DEFAULT_REGION = Regions.EU_WEST_1;

    private AwsSystemProperties() {
        // utility class
    }

    public static EndpointConfiguration toEndpointConfiguration(final String uri) {
        return new EndpointConfiguration(uri, loadRegion().getName());
    }

    public static String loadEnvironment() {
        return loadSystemProperty().orElse("dev");
    }

    public static Regions loadRegion() {
        return loadRegionValue()
                .map(Regions::fromName)
                .orElseGet(AwsSystemProperties::loadDefaultRegion);
    }

    private static Optional<String> loadRegionValue() {
        return SystemPropertyLoader.load(REGION);
    }

    private static Regions loadDefaultRegion() {
        log.info("{} system property not set returning default value {}", REGION, DEFAULT_REGION);
        return DEFAULT_REGION;
    }

    private static Optional<String> loadSystemProperty() {
        return SystemPropertyLoader.load(ENVIRONMENT);
    }

}
