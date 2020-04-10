package uk.co.idv.json.onetimepasscode;

import uk.co.idv.json.ObjectMapperFactory;

public class OneTimePasscodeObjectMapperFactory extends ObjectMapperFactory {

    public OneTimePasscodeObjectMapperFactory() {
        super(new OneTimePasscodeModuleProvider().getModules());
    }

}
