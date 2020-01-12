package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AwsSystemPropertiesTest {

    @Test
    @ClearSystemProperty(key = "aws.region")
    void shouldLoadDefaultRegionIfPropertyNotSet() {
        final Regions region = AwsSystemProperties.loadRegion();

        assertThat(region).isEqualTo(Regions.EU_WEST_1);
    }

    @Test
    @SetSystemProperty(key = "aws.region", value = "us-west-2")
    void shouldLoadSpecifiedRegionIfPropertySet() {
        final Regions region = AwsSystemProperties.loadRegion();

        assertThat(region).isEqualTo(Regions.US_WEST_2);
    }

    @Test
    @ClearSystemProperty(key = "aws.dynamo.db.endpoint.uri")
    void shouldLoadEmptyDynamoDbEndpointConfigurationIfPropertyNotSet() {
        final Optional<EndpointConfiguration> config = AwsSystemProperties.loadDynamoDbEndpointConfiguration();

        assertThat(config).isEmpty();
    }

    @Test
    @SetSystemProperty(key = "aws.dynamo.db.endpoint.uri", value = "http://localhost:8000")
    @SetSystemProperty(key = "aws.region", value = "us-west-2")
    void shouldLoadDynamoDbEndpointConfigurationIfPropertiesSet() {
        final Optional<EndpointConfiguration> result = AwsSystemProperties.loadDynamoDbEndpointConfiguration();

        assertThat(result.isPresent()).isTrue();
        final EndpointConfiguration config = result.get();
        assertThat(config.getServiceEndpoint()).isEqualTo("http://localhost:8000");
        assertThat(config.getSigningRegion()).isEqualTo("us-west-2");
    }

    @Test
    @ClearSystemProperty(key = "environment")
    void shouldLoadDefaultEnvironmentIfPropertyNotSet() {
        final String environment = AwsSystemProperties.loadEnvironment();

        assertThat(environment).isEqualTo("dev");
    }

    @Test
    @SetSystemProperty(key = "environment", value = "sit")
    void shouldLoadEnvironmentIfPropertySet() {
        final String environment = AwsSystemProperties.loadEnvironment();

        assertThat(environment).isEqualTo("sit");
    }

}
