package uk.co.idv.uk.config;

import uk.co.idv.json.DefaultModuleProvider;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.uk.api.channel.UkChannelModule;

public class UkModuleProvider extends DefaultModuleProvider {

    public UkModuleProvider() {
        super(new OneTimePasscodeModule(), new UkChannelModule());
    }

}
