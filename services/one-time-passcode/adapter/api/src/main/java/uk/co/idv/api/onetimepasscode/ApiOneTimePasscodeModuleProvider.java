package uk.co.idv.api.onetimepasscode;

import uk.co.idv.json.DefaultModuleProvider;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;

public class ApiOneTimePasscodeModuleProvider extends DefaultModuleProvider {

    public ApiOneTimePasscodeModuleProvider() {
        super(new OneTimePasscodeModule(), new ApiOneTimePasscodeModule());
    }

}
