package uk.co.idv.uk.api.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.api.lockout.JsonApiLockoutStateModule;
import uk.co.idv.api.verificationcontext.JsonApiVerificationContextModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.jsonapi.ApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class UkObjectMapperSingleton {

    private static ObjectMapper MAPPER = build();

    public static ObjectMapper get() {
        return MAPPER;
    }

    private static ObjectMapper build() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ApiModule());
        mapper.registerModule(new UkChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new JsonApiLockoutStateModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
