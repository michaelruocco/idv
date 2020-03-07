package uk.co.idv.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.lockout.LockoutStateModule;
import uk.co.idv.api.verificationcontext.ApiVerificationContextModule;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.jsonapi.ApiModule;

public class ApiObjectMapperSingleton {

    private static final ObjectMapperFactory FACTORY = new ObjectMapperFactory();
    private static ObjectMapper MAPPER;

    private ApiObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper instance() {
        if (MAPPER == null) {
            MAPPER = build();
        }
        return MAPPER;
    }

    private static ObjectMapper build() {
        return customize(FACTORY.build());
    }

    private static ObjectMapper customize(final ObjectMapper mapper) {
        return mapper.registerModules(
                new ApiModule(),
                new ApiVerificationContextModule(),
                new LockoutStateModule()
        );
    }

}
