package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.ObjectMapperSingleton;

public class UkObjectMapperSingleton {

    private static final ObjectMapper MAPPER = build();

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper build() {
        final ObjectMapper mapper = ObjectMapperSingleton.instance();
        mapper.registerModule(new UkChannelModule());
        return mapper;
    }

}
