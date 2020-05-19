package uk.co.idv.repository.redis.verificationcontext.policy;

import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.repository.redis.policy.RedissionPolicyDao;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Map;
import java.util.UUID;

public class RedissonVerificationPolicyDao extends RedissionPolicyDao<VerificationPolicy> implements VerificationPolicyDao {

    public RedissonVerificationPolicyDao(final Map<UUID, String> policies,
                                         final JsonConverter jsonConverter) {
        super(policies, jsonConverter, VerificationPolicy.class);
    }

}
