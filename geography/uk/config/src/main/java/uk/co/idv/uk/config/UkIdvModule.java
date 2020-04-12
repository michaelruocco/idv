package uk.co.idv.uk.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.uk.api.channel.UkChannelModule;

import java.util.Arrays;

public class UkIdvModule extends SimpleModule {

    public UkIdvModule() {
        super("uk-idv-module", Version.unknownVersion());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new UkChannelModule(),
                new VerificationContextModule(),
                new LockoutPolicyModule(),
                new VerificationAttemptsModule(),
                new OneTimePasscodeModule()
        );
    }

}
