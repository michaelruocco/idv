package uk.co.idv.json.lockout.policy;

import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class LockoutPolicyObjectMapperFactory extends ObjectMapperFactory {

    public LockoutPolicyObjectMapperFactory() {
        super(new LockoutPolicyModule());
    }
}
