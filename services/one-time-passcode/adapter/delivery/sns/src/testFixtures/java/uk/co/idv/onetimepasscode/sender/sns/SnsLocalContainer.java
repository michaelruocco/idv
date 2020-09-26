package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class SnsLocalContainer extends GenericContainer<SnsLocalContainer> {

    private final Regions region;

    public SnsLocalContainer() {
        this(Regions.EU_WEST_1);
    }

    public SnsLocalContainer(final Regions region) {
        super("localstack/localstack:latest");
        withExposedPorts(4566);
        withEnv("SERVICES", "sns");
        this.region = region;
    }

    public AmazonSNS buildSnsClient() {
        final AWSCredentials credentials = new BasicAWSCredentials("abc", "123");
        final AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        final SnsClientFactory factory = new SnsClientFactory(credentialsProvider);
        return factory.withEndpointConfiguration(buildEndpointConfiguration());
    }

    public String buildEndpointUri() {
        final String uri = String.format("http://%s:%s", getContainerIpAddress(), getFirstMappedPort());
        log.info("connecting to dynamo db using uri {}", uri);
        return uri;
    }

    private EndpointConfiguration buildEndpointConfiguration() {
        return new EndpointConfiguration(buildEndpointUri(), region.getName());
    }

}
