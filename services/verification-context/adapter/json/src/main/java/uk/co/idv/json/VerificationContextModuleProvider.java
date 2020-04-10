package uk.co.idv.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.cardnumber.CardNumberModule;
import uk.co.idv.json.channel.simple.SimpleChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.idv.utils.json.jackson.ModuleProvider;

import java.util.Arrays;
import java.util.Collection;

public class VerificationContextModuleProvider implements ModuleProvider {

    @Override
    public Collection<Module> getModules() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new MoneyModule(),
                new CardNumberModule(),
                new SimpleChannelModule(),
                new ActivityModule(),
                new IdentityModule(),
                new VerificationContextModule(),
                new VerificationAttemptsModule(),
                new LockoutPolicyModule()
        );
    }

}
