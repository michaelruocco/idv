package uk.co.idv.config.uk.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class UkJacksonConfig {

    public ObjectMapper apiObjectMapper() {
        return new ObjectMapperFactory(new UkApiIdvModule()).build();
    }

    public ObjectMapper persistenceObjectMapper() {
        return new ObjectMapperFactory(new UkIdvModule()).build();
    }

}
