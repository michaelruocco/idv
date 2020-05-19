package uk.co.idv.repository.redis.verificationcontext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class VerificationContextRedisConfig {

    private final RedissonClient client;

    public VerificationContextRedisConfig() {
        this(new RedissonClientFactory().build());
    }

    public VerificationPolicyDao verificationPolicyDao(final JsonConverter jsonConverter) {
        final RMap<UUID, String> map = getLocalCachedMap("verification-policies");
        return new RedissonVerificationPolicyDao(jsonConverter, map);
    }

    private RMap<UUID, String> getLocalCachedMap(final String name) {
        return client.getLocalCachedMap(name, LocalCachedMapOptions.defaults());
    }

}
