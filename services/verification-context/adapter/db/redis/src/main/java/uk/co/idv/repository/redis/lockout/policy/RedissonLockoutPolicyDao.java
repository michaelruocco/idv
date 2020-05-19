package uk.co.idv.repository.redis.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.repository.redis.policy.RedissionPolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Map;
import java.util.UUID;

public class RedissonLockoutPolicyDao extends RedissionPolicyDao<LockoutPolicy> implements LockoutPolicyDao {

    public RedissonLockoutPolicyDao(final Map<UUID, String> policies,
                                    final JsonConverter jsonConverter) {
        super(policies, jsonConverter, LockoutPolicy.class);
    }

}
