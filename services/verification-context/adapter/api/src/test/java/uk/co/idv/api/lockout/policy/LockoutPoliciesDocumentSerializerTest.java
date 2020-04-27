package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.ApiVerificationContextObjectMapperFactory;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPoliciesDocumentSerializerTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final LockoutPolicy attributes = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPoliciesDocument document = new LockoutPoliciesDocument(attributes);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-policies-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
