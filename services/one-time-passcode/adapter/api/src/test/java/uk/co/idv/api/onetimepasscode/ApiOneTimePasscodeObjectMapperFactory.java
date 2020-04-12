package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class ApiOneTimePasscodeObjectMapperFactory {

    public static ObjectMapper build() {
        return new ObjectMapperFactory(new ApiOneTimePasscodeModule()).build();
    }

}
