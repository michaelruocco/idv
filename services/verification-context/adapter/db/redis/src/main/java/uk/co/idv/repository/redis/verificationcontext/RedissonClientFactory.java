package uk.co.idv.repository.redis.verificationcontext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@RequiredArgsConstructor
@Slf4j
public class RedissonClientFactory {

    public RedissonClient build() {
        final Config config = new Config();
        config.useSingleServer().setAddress(RedisSystemProperties.loadRedisEndpointUri());
        return Redisson.create(config);
    }

}
