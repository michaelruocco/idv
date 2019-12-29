package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.json.ObjectMapperSingleton;

public class UkObjectMapperSingleton {

    private static final ObjectMapper JSON_API_MAPPER = buildJsonApi();
    private static final ObjectMapper MAPPER = build();

    private UkObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper jsonApiInstance() {
        return JSON_API_MAPPER;
    }

    public static ObjectMapper instance() {
        return MAPPER;
    }

    private static ObjectMapper buildJsonApi() {
        return customize(ApiObjectMapperSingleton.instance());
    }

    private static ObjectMapper build() {
        return customize(ObjectMapperSingleton.instance());
    }

    private static ObjectMapper customize(final ObjectMapper mapper) {
        return mapper.registerModule(new UkChannelModule());
    }

}
