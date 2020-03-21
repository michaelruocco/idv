package uk.co.idv.onetimepasscode.sender.sns;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import lombok.RequiredArgsConstructor;
import uk.co.idv.utils.aws.system.AwsSystemProperties;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@RequiredArgsConstructor
public class SnsClientFactory {

    private final Regions region;
    private final AWSCredentialsProvider credentialsProvider;

    public SnsClientFactory() {
        this(new DefaultAWSCredentialsProviderChain());
    }

    public SnsClientFactory(final AWSCredentialsProvider credentialsProvider) {
        this(AwsSystemProperties.loadRegion(), credentialsProvider);
    }

    public AmazonSNS build() {
        return SnsSystemProperties.loadSnsEndpointConfiguration()
                .map(this::withEndpointConfiguration)
                .orElse(standard());
    }

    public AmazonSNS standard() {
        return builder()
                .withRegion(region)
                .build();
    }

    public AmazonSNS withEndpointConfiguration(final EndpointConfiguration endpointConfiguration) {
        return builder()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    private AmazonSNSClientBuilder builder() {
        return AmazonSNSClientBuilder.standard()
                .withCredentials(credentialsProvider);
    }

}
