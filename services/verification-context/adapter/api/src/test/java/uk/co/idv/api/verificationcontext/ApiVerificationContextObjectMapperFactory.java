package uk.co.idv.api.verificationcontext;

import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class ApiVerificationContextObjectMapperFactory extends ObjectMapperFactory {

    public ApiVerificationContextObjectMapperFactory() {
        super(new ApiVerificationContextModule());
    }

}
