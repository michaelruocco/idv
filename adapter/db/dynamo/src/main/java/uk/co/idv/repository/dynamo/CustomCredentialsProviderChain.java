package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;

public class CustomCredentialsProviderChain extends AWSCredentialsProviderChain {

    private static final AWSCredentialsProviderChain INSTANCE
            = new CustomCredentialsProviderChain();

    public CustomCredentialsProviderChain() {
        super(new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                InstanceProfileCredentialsProvider.getInstance());
    }

    public static AWSCredentialsProviderChain getInstance() {
        return INSTANCE;
    }

}
