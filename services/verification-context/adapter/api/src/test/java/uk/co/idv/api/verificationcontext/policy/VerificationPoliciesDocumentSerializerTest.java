package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.ApiVerificationContextObjectMapperFactory;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyMother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationPoliciesDocumentSerializerTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final VerificationPolicy attributes = VerificationPolicyMother.build();
        final VerificationPoliciesDocument document = new VerificationPoliciesDocument(attributes);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/policy/verification-policies-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
