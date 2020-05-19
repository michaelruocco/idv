package uk.co.idv.repository.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.repository.redis.lockout.policy.RedissonLockoutPolicyDao;
import uk.co.idv.repository.redis.verificationcontext.policy.RedissonVerificationPolicyDao;
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
        final RMap<UUID, String> map = client.getLocalCachedMap("verification-policies", LocalCachedMapOptions.defaults());
        return new RedissonVerificationPolicyDao(map, jsonConverter);
    }

    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        final RMap<UUID, String> map = client.getLocalCachedMap("lockout-policies", LocalCachedMapOptions.defaults());
        return new RedissonLockoutPolicyDao(map, jsonConverter);
    }

}
