package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.json.VerificationContextModuleProvider;
import uk.co.idv.json.activity.AllowFakeActivityModule;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Collection;
import java.util.Collections;

public class AllowFakeActivityModuleProvider extends VerificationContextModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Collections.singleton(new AllowFakeActivityModule());
    }

}
