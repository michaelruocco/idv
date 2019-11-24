package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldSerializePolicies() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/hard/create-hard-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicyAttributes attributes = document.getAttributes();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.hardLock();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "lockoutLevel");
        LockoutAssertions.assertThat(attributes.getLockoutLevel()).isEqualTo(expectedAttributes.getLockoutLevel());
    }

}
