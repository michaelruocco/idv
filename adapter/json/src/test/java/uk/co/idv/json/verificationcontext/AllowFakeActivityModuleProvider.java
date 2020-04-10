package uk.co.idv.json.verificationcontext;

import uk.co.idv.json.DefaultModuleProvider;
import uk.co.idv.json.activity.AllowFakeActivityModule;

public class AllowFakeActivityModuleProvider extends DefaultModuleProvider {

    public AllowFakeActivityModuleProvider() {
        super(new AllowFakeActivityModule());
    }

}
