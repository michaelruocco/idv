package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.Module;
import uk.co.idv.json.VerificationContextModuleProvider;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.uk.api.channel.UkChannelModule;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Arrays;
import java.util.Collection;

public class UkModuleProvider extends VerificationContextModuleProvider {

    private static final Collection<Module> MODULES = buildModules();

    @Override
    public Collection<Module> getModules() {
        return ModuleProvider.merge(super.getModules(), MODULES);
    }

    private static Collection<Module> buildModules() {
        return Arrays.asList(new OneTimePasscodeModule(), new UkChannelModule());
    }

}
