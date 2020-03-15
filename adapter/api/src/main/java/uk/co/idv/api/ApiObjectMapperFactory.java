package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.api.lockout.LockoutStateModule;
import uk.co.idv.api.verification.onetimepasscode.ApiOneTimePasscodeModule;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.cardnumber.CardNumberModule;
import uk.co.idv.json.channel.simple.SimpleChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.verification.onetimepasscode.OneTimePasscodeModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@RequiredArgsConstructor
public class ApiObjectMapperFactory extends ObjectMapperFactory {

    private final List<Module> modules;

    public ApiObjectMapperFactory() {
        this(defaultModules());
    }

    public ApiObjectMapperFactory(final Module... modules) {
        this(Arrays.asList(modules));
    }

    public ObjectMapper build() {
        return customize(new ObjectMapper());
    }

    private ObjectMapper customize(final ObjectMapper mapper) {
        return mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(modules);
    }

    public static List<Module> defaultModules() {
        return modules(new ActivityModule());
    }

    public static List<Module>  modules

    public static List<Module> modules(final ActivityModule activityModule) {
        final List<Module> jsonList = ObjectMapperFactory.modules(activityModule);
        final List<Module> modules = Arrays.asList(
                new ApiModule(),
                new ApiVerificationContextModule(),
                new ApiOneTimePasscodeModule(),
                new LockoutStateModule()
        );
        CollectionUtils.con
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new MoneyModule(),
                new CardNumberModule(),
                new SimpleChannelModule(),
                activityModule,
                new IdentityModule(),
                new VerificationContextModule(),
                new VerificationAttemptsModule(),
                new LockoutPolicyModule(),
                new OneTimePasscodeModule()
        );
    }

}
