package uk.co.idv.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.json.ObjectMapperFactory;


public class ApiObjectMapperSingleton {

    private static final ObjectMapperFactory FACTORY = new ApiObjectMapperFactory();
    private static ObjectMapper MAPPER;

    private ApiObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper instance() {
        if (MAPPER == null) {
            MAPPER = FACTORY.build();
        }
        return MAPPER;
    }

}
