package uk.co.idv.json.onetimepasscode;

import uk.co.idv.json.DefaultModuleProvider;

public class OneTimePasscodeModuleProvider extends DefaultModuleProvider {

    public OneTimePasscodeModuleProvider() {
        super(new OneTimePasscodeModule());
    }

}
