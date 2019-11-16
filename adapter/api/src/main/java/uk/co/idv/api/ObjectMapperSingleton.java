package uk.co.idv.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.api.lockout.JsonApiLockoutStateModule;
import uk.co.idv.api.verificationcontext.JsonApiVerificationContextModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.simple.SimpleChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.jsonapi.ApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ObjectMapperSingleton {

    private static ObjectMapper MAPPER = build();

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper build() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(
                new Jdk8Module(),
                new JavaTimeModule(),
                new MoneyModule(),
                new ApiModule(),
                new SimpleChannelModule(),
                new ActivityModule(),
                new IdentityModule(),
                new JsonApiVerificationContextModule(),
                new JsonApiLockoutStateModule()
        );
        mapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
