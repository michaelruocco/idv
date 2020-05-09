package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationPolicySerializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializePolicy() throws JsonProcessingException {
        final VerificationPolicy policy = VerificationPolicyMother.build();

        final String json = MAPPER.writeValueAsString(policy);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/policy/verification-policy.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
