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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DefaultModuleProvider implements ModuleProvider {

    private final Collection<Module> modules;

    public DefaultModuleProvider(final Module... additionalModules) {
        this(Arrays.asList(additionalModules));
    }

    public DefaultModuleProvider(final Collection<Module> additionalModules) {
        this.modules = appendToDefaultModules(additionalModules);
    }

    public Collection<Module> getModules() {
        return modules;
    }

    private static Collection<Module> appendToDefaultModules(final Collection<Module> additionalModules) {
        final Collection<Module> allModules = defaultModules();
        allModules.addAll(additionalModules);
        return Collections.unmodifiableCollection(allModules);
    }

    private static Collection<Module> defaultModules() {
        return new ArrayList<>(Arrays.asList(
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
        ));
    }

}
