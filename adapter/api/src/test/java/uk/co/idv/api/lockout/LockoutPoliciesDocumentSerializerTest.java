package uk.co.idv.api.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.json.lockout.LockoutPolicyParametersMother;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.json.lockout.LockoutPolicyParameters;
import uk.co.mruoc.jsonapi.ApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPoliciesDocumentSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();
        final LockoutPoliciesDocument document = new LockoutPoliciesDocument(parameters);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/json-api/max-attempts-alias-level-lockout-policies-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ApiModule());
        mapper.registerModule(new JsonApiLockoutStateModule());
        mapper.registerModule(new ChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
