package uk.co.idv.uk.config;

import uk.co.idv.api.ApiModuleProvider;
import uk.co.idv.api.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.uk.api.channel.UkChannelModule;

public class UkApiModuleProvider extends ApiModuleProvider {

    public UkApiModuleProvider() {
        super(
                new ApiOneTimePasscodeModule(),
                new OneTimePasscodeModule(),
                new UkChannelModule()
        );
    }

}
