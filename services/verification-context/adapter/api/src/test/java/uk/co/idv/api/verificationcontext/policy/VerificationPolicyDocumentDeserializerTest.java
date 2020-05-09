package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.ApiVerificationContextObjectMapperFactory;
import uk.co.idv.domain.entities.policy.assertion.PolicyAssertions;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationPolicyDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializePolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/policy/verification-policy-document.json");

        final VerificationPolicyDocument document = MAPPER.readValue(json, VerificationPolicyDocument.class);

        final VerificationPolicy attributes = document.getAttributes();
        final VerificationPolicy expectedAttributes = VerificationPolicyMother.build();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "level", "sequencePolicies");
        PolicyAssertions.assertThat(attributes.getLevel()).isEqualTo(expectedAttributes.getLevel());
        assertThat(attributes.getSequencePolicies()).containsExactlyElementsOf(expectedAttributes.getSequencePolicies());
    }

}
