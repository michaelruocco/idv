package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.utils.json.jackson.ObjectMapperFactory;

public class ApiOneTimePasscodeObjectMapperFactory {

    public static ObjectMapper build() {
        return new ObjectMapperFactory(new ApiOneTimePasscodeModuleProvider()).build();
    }

}
