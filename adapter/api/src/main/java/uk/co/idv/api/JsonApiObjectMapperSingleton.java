package uk.co.idv.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.verificationcontext.JsonApiVerificationContextModule;
import uk.co.idv.json.ObjectMapperSingleton;

public class JsonApiObjectMapperSingleton {

    private static ObjectMapper MAPPER;

    private JsonApiObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper instance() {
        if (MAPPER == null) {
            MAPPER = build();
        }
        return MAPPER;
    }

    private static ObjectMapper build() {
        return customize(ObjectMapperSingleton.customize(new ObjectMapper()));
    }

    private static ObjectMapper customize(final ObjectMapper mapper) {
        return mapper.registerModule(new JsonApiVerificationContextModule());
    }

}
