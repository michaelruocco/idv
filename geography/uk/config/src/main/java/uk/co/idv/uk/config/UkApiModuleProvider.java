package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.api.ApiVerificationContextModuleProvider;
import uk.co.idv.api.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.uk.api.channel.UkChannelModule;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Arrays;
import java.util.Collection;

public class UkApiModuleProvider extends ApiVerificationContextModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Arrays.asList(
                new ApiOneTimePasscodeModule(),
                new OneTimePasscodeModule(),
                new UkChannelModule()
        );
    }

}
