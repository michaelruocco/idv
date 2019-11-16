package uk.co.idv.api.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.idv.json.lockout.LockoutPolicyParametersMother;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.json.lockout.LockoutPolicyParameters;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPoliciesDocumentSerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();
        final LockoutPoliciesDocument document = new LockoutPoliciesDocument(parameters);

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/json-api/max-attempts-alias-level-lockout-policies-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
