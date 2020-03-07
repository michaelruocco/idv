package uk.co.idv.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.Module;
import lombok.RequiredArgsConstructor;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.simple.SimpleChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;

import java.util.List;
import java.util.Arrays;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@RequiredArgsConstructor
public class ObjectMapperFactory {

    private final List<Module> modules;

    public ObjectMapperFactory() {
        this(defaultModules());
    }

    public ObjectMapperFactory(final Module... modules) {
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

    public static List<Module> modules(final ActivityModule activityModule) {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule(),
                new MoneyModule(),
                new SimpleChannelModule(),
                activityModule,
                new IdentityModule(),
                new VerificationContextModule(),
                new VerificationAttemptsModule(),
                new LockoutPolicyModule()
        );
    }

}
