package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.uk.domain.entities.policy.lockout.UkLockoutPolicyProvider;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class UkConfig {

    public ObjectMapper jsonApiObjectMapper() {
        return new ObjectMapperFactory(new UkApiIdvModule()).build();
    }

    public ObjectMapper objectMapper() {
        return new ObjectMapperFactory(new UkIdvModule()).build();
    }

    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    public LockoutPolicyProvider lockoutPolicyProvider() {
        return new UkLockoutPolicyProvider();
    }

}
