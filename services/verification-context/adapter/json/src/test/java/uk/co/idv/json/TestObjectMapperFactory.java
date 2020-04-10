package uk.co.idv.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.utils.json.jackson.ObjectMapperFactory;

public class TestObjectMapperFactory {

    public static ObjectMapper build() {
        return new ObjectMapperFactory(new VerificationContextModuleProvider()).build();
    }

}
