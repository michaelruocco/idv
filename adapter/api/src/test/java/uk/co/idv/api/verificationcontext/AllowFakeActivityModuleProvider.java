package uk.co.idv.api.verificationcontext;

import uk.co.idv.api.ApiModuleProvider;
import uk.co.idv.json.activity.AllowFakeActivityModule;

public class AllowFakeActivityModuleProvider extends ApiModuleProvider {

    public AllowFakeActivityModuleProvider() {
        super(new AllowFakeActivityModule());
    }

}
