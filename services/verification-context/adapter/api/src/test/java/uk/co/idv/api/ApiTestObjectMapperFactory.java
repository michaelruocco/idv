package uk.co.idv.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.utils.json.jackson.ObjectMapperFactory;

public class ApiTestObjectMapperFactory {

    public static ObjectMapper build() {
        return new ObjectMapperFactory(new ApiVerificationContextModuleProvider()).build();
    }

}
