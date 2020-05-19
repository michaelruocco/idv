package uk.co.idv.repository.redis;

import uk.co.idv.utils.aws.system.SystemPropertyLoader;


public class RedisSystemProperties {

    private static final String REDIS_ENDPOINT_URI = "redis.endpoint.uri";
    private static final String DEFAULT_VALUE = "redis://localhost:6379";

    private RedisSystemProperties() {
        // utility class
    }

    public static String loadRedisEndpointUri() {
        return SystemPropertyLoader.load(REDIS_ENDPOINT_URI).orElse(DEFAULT_VALUE);
    }

}
