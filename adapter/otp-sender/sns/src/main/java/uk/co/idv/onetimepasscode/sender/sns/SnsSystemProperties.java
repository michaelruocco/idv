package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.utils.aws.system.AwsSystemProperties;
import uk.co.idv.utils.aws.system.SystemPropertyLoader;

import java.util.Optional;


@Slf4j
public class SnsSystemProperties {

    private static final String SNS_ENDPOINT_URI = "aws.sns.endpoint.uri";

    private SnsSystemProperties() {
        // utility class
    }

    public static Optional<EndpointConfiguration> loadSnsEndpointConfiguration() {
        return loadSnsEndpointUri().map(AwsSystemProperties::toEndpointConfiguration);
    }

    private static Optional<String> loadSnsEndpointUri() {
        return SystemPropertyLoader.load(SNS_ENDPOINT_URI);
    }

}
