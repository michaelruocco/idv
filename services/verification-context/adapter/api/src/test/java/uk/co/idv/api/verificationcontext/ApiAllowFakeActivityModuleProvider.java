package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.api.ApiVerificationContextModuleProvider;
import uk.co.idv.json.activity.AllowFakeActivityModule;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Collection;
import java.util.Collections;

public class ApiAllowFakeActivityModuleProvider extends ApiVerificationContextModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Collections.singleton(new AllowFakeActivityModule());
    }

}
