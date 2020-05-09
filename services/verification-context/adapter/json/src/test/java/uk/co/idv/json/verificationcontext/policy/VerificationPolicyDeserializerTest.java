package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.assertion.PolicyAssertions;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationPolicyDeserializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializePolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/policy/verification-policy.json");

        final VerificationPolicy policy = MAPPER.readValue(json, VerificationPolicy.class);

        final VerificationPolicy expectedPolicy = VerificationPolicyMother.build();
        assertThat(policy).isEqualToIgnoringGivenFields(expectedPolicy, "level", "sequencePolicies");
        PolicyAssertions.assertThat(policy.getLevel()).isEqualTo(expectedPolicy.getLevel());
        assertThat(policy.getSequencePolicies()).containsExactlyElementsOf(expectedPolicy.getSequencePolicies());
    }

}
