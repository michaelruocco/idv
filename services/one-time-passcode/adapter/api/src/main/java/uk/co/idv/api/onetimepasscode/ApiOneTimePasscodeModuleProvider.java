package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModuleProvider;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Collection;
import java.util.Collections;

public class ApiOneTimePasscodeModuleProvider extends OneTimePasscodeModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Collections.singleton(new ApiOneTimePasscodeModule());
    }

}
