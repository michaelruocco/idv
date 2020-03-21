package uk.co.idv.utils.aws.system;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import static org.assertj.core.api.Assertions.assertThat;

class AwsSystemPropertiesTest {

    @Test
    @ClearSystemProperty(key = "aws.region")
    void shouldConvertUriToEndpointConfig() {
        final String uri = "uri";

        final EndpointConfiguration config = AwsSystemProperties.toEndpointConfiguration(uri);

        assertThat(config.getServiceEndpoint()).isEqualTo(uri);
    }

    @Test
    @ClearSystemProperty(key = "aws.region")
    void shouldSetDefaultRegionOnEndpointConfigIfRegionNotSet() {
        final String uri = "uri";

        final EndpointConfiguration config = AwsSystemProperties.toEndpointConfiguration(uri);

        assertThat(config.getSigningRegion()).isEqualTo(Regions.EU_WEST_1.getName());
    }

    @Test
    @SetSystemProperty(key = "aws.region", value = "us-west-2")
    void shouldSetSpecifiedRegionOnEndpointConfigIfPropertySet() {
        final String uri = "uri";

        final EndpointConfiguration config = AwsSystemProperties.toEndpointConfiguration(uri);

        assertThat(config.getSigningRegion()).isEqualTo(Regions.US_WEST_2.getName());
    }

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
