package uk.co.idv.repository.redis.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.testcontainers.containers.GenericContainer;

@Slf4j
public class RedissonLocalContainer extends GenericContainer<RedissonLocalContainer> {

    public RedissonLocalContainer() {
        super("redis:latest");
        withExposedPorts(6379);
    }

    public RedissonClient buildClient() {
        final Config config = new Config();
        config.useSingleServer().setAddress(buildEndpointUri());
        return Redisson.create(config);
    }

    public String buildEndpointUri() {
        final String uri = String.format("redis://%s:%s", getContainerIpAddress(), getFirstMappedPort());
        log.info("connecting to redis using uri {}", uri);
        return uri;
    }

}
