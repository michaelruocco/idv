package uk.co.idv.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {

    private static final ObjectMapperFactory FACTORY = new ObjectMapperFactory();
    private static ObjectMapper mapper;

    private ObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper instance() {
        if (mapper == null) {
            mapper = FACTORY.build();
        }
        return mapper;
    }

}
