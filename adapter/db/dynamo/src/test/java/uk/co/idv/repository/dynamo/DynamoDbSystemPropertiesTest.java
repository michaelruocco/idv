package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoDbSystemPropertiesTest {

    @Test
    @ClearSystemProperty(key = "aws.dynamo.db.endpoint.uri")
    void shouldLoadEmptyDynamoDbEndpointConfigurationIfPropertyNotSet() {
        final Optional<EndpointConfiguration> config = DynamoDbSystemProperties.loadDynamoDbEndpointConfiguration();

        assertThat(config).isEmpty();
    }

    @Test
    @SetSystemProperty(key = "aws.dynamo.db.endpoint.uri", value = "http://localhost:8000")
    @SetSystemProperty(key = "aws.region", value = "us-west-2")
    void shouldLoadDynamoDbEndpointConfigurationIfPropertiesSet() {
        final Optional<EndpointConfiguration> result = DynamoDbSystemProperties.loadDynamoDbEndpointConfiguration();

        assertThat(result.isPresent()).isTrue();
        final EndpointConfiguration config = result.get();
        assertThat(config.getServiceEndpoint()).isEqualTo("http://localhost:8000");
        assertThat(config.getSigningRegion()).isEqualTo("us-west-2");
    }

}
