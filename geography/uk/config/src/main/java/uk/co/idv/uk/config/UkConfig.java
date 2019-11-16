package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;

public class UkConfig {

    public ObjectMapper objectMapper() {
        return UkObjectMapperSingleton.get();
    }

    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    public LockoutPolicyProvider lockoutPolicyProvider() {
        return new UkLockoutPolicyProvider();
    }

}
