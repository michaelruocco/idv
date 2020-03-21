package uk.co.idv.repository.dynamo;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.utils.aws.system.AwsSystemProperties;
import uk.co.idv.utils.aws.system.SystemPropertyLoader;

import java.util.Optional;


@Slf4j
public class DynamoDbSystemProperties {

    private static final String DYNAMO_DB_ENDPOINT_URI = "aws.dynamo.db.endpoint.uri";

    private DynamoDbSystemProperties() {
        // utility class
    }

    public static Optional<EndpointConfiguration> loadDynamoDbEndpointConfiguration() {
        return loadDynamoDbEndpointUri().map(AwsSystemProperties::toEndpointConfiguration);
    }

    private static Optional<String> loadDynamoDbEndpointUri() {
        return SystemPropertyLoader.load(DYNAMO_DB_ENDPOINT_URI);
    }

}
