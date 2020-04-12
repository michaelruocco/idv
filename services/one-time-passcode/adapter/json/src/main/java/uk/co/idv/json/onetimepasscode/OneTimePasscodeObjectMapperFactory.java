package uk.co.idv.json.onetimepasscode;

import uk.co.idv.json.verificationcontext.method.onetimepasscode.OneTimePasscodeMethodModule;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

public class OneTimePasscodeObjectMapperFactory extends ObjectMapperFactory {

    public OneTimePasscodeObjectMapperFactory() {
        super(
                new OneTimePasscodeModule(),
                new OneTimePasscodeMethodModule()
        );
    }

}
