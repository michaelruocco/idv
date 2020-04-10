package uk.co.idv.api;

import uk.co.idv.json.ObjectMapperFactory;

public class ApiObjectMapperFactory extends ObjectMapperFactory {

    public ApiObjectMapperFactory() {
        super(new ApiModuleProvider());
    }

}
