package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.FakeChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.file.content.ContentLoader;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationContextSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeContext() throws JsonProcessingException {
        final VerificationContext context = new FakeVerificationContext();

        final String json = MAPPER.writeValueAsString(context);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-context.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        mapper.registerModule(new FakeChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
