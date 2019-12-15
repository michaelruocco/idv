package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.JsonApiObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPoliciesDocumentSerializerTest {

    private static final ObjectMapper MAPPER = JsonApiObjectMapperSingleton.instance();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final LockoutPoliciesDocument document = new LockoutPoliciesDocument(attributes);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-policies-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
