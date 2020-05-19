package uk.co.idv.repository.redis;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import static org.assertj.core.api.Assertions.assertThat;

class RedisSystemPropertiesTest {

    @Test
    @ClearSystemProperty(key = "redis.endpoint.uri")
    void shouldLoadDefaultRedisUriIfPropertyNotSet() {
        final String uri = RedisSystemProperties.loadRedisEndpointUri();

        assertThat(uri).isEqualTo("redis://localhost:6379");
    }

    @Test
    @SetSystemProperty(key = "redis.endpoint.uri", value = "redis://localhost:8000")
    void shouldLoadConfiguredRedisUriIfPropertySet() {
        final String uri = RedisSystemProperties.loadRedisEndpointUri();

        assertThat(uri).isEqualTo("redis://localhost:8000");
    }

}
