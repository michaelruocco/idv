package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
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
        withExposedPorts(4575);
        withEnv("SERVICES", "sns");
        this.region = region;
    }

    public AmazonSNS buildSnsClient() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(buildEndpointConfiguration())
                .build();
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
