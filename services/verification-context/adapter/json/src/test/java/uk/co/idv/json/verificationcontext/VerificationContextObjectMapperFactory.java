package uk.co.idv.json.verificationcontext;

import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class VerificationContextObjectMapperFactory extends ObjectMapperFactory {

    public VerificationContextObjectMapperFactory() {
        super(new VerificationContextModule());
    }

}
