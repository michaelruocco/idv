package uk.co.idv.config.uk.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.api.lockout.ApiLockoutStateModule;
import uk.co.idv.api.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.api.verificationcontext.ApiVerificationContextModule;
import uk.co.idv.api.verificationcontext.policy.ApiVerificationPolicyModule;

import java.util.Arrays;

public class UkApiIdvModule extends SimpleModule {

    public UkApiIdvModule() {
        super("uk-api-idv-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ApiVerificationContextModule(),
                new ApiOneTimePasscodeModule(),
                new ApiVerificationPolicyModule(),
                new ApiLockoutStateModule(),
                new UkIdvModule()
        );
    }

}
