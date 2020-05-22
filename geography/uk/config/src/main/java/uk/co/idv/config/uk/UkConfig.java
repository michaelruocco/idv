package uk.co.idv.config.uk;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.lockout.UkLockoutPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.sequence.UkVerificationPolicyProvider;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class UkConfig {

    public ObjectMapper apiObjectMapper() {
        return new ObjectMapperFactory(new UkApiIdvModule()).build();
    }

    public ObjectMapper persistenceObjectMapper() {
        return new ObjectMapperFactory(new UkIdvModule()).build();
    }

    public LockoutPolicyProvider lockoutPolicyProvider() {
        return new UkLockoutPolicyProvider();
    }

    public VerificationPolicyProvider verificationPolicyProvider() {
        return new UkVerificationPolicyProvider();
    }

}
