package uk.co.idv.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.simple.SimpleChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsModule;
import uk.co.idv.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.json.verificationcontext.VerificationContextModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ObjectMapperSingleton {

    private static ObjectMapper MAPPER;

    private ObjectMapperSingleton() {
        // utility class
    }

    public static ObjectMapper instance() {
        if (MAPPER == null) {
            MAPPER = build();
        }
        return MAPPER;
    }

    public static ObjectMapper customize(final ObjectMapper mapper) {
        return mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(
                        new Jdk8Module(),
                        new JavaTimeModule(),
                        new MoneyModule(),
                        new SimpleChannelModule(),
                        new ActivityModule(),
                        new IdentityModule(),
                        new VerificationContextModule(),
                        new VerificationAttemptsModule(),
                        new LockoutPolicyModule()
                );
    }

    private static ObjectMapper build() {
        return customize(new ObjectMapper());
    }

}
