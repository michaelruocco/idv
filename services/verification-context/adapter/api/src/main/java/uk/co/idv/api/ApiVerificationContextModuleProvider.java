package uk.co.idv.api;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.api.lockout.ApiLockoutStateModule;
import uk.co.idv.api.verificationcontext.ApiVerificationContextModule;
import uk.co.idv.json.VerificationContextModuleProvider;
import uk.co.idv.utils.json.jackson.ModuleProvider;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.Arrays;
import java.util.Collection;

public class ApiVerificationContextModuleProvider extends VerificationContextModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Arrays.asList(
                new ApiModule(),
                new ApiVerificationContextModule(),
                new ApiLockoutStateModule()
        );
    }

}
