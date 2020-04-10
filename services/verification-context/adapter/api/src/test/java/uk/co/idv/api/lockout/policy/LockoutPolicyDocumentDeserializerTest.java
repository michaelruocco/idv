package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiTestObjectMapperFactory;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LockoutPolicyDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ApiTestObjectMapperFactory.build();

    @Test
    void shouldDeserializeHardLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicyAttributes attributes = document.getAttributes();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.hardLock();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "lockoutLevel");
        LockoutAssertions.assertThat(attributes.getLockoutLevel()).isEqualTo(expectedAttributes.getLockoutLevel());
    }

    @Test
    void shouldDeserializeNonLockingPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/non-locking/non-locking-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicyAttributes attributes = document.getAttributes();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.nonLocking();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "lockoutLevel");
        LockoutAssertions.assertThat(attributes.getLockoutLevel()).isEqualTo(expectedAttributes.getLockoutLevel());
    }

    @Test
    void shouldDeserializeSoftLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/soft/soft-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicyAttributes attributes = document.getAttributes();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.softLock();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "lockoutLevel");
        LockoutAssertions.assertThat(attributes.getLockoutLevel()).isEqualTo(expectedAttributes.getLockoutLevel());
    }

    @Test
    void shouldDeserializeRecurringSoftLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/soft/recurring-soft-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicyAttributes attributes = document.getAttributes();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.recurringSoftLock();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "lockoutLevel");
        LockoutAssertions.assertThat(attributes.getLockoutLevel()).isEqualTo(expectedAttributes.getLockoutLevel());
    }

    @Test
    void shouldThrowExceptionIfLockoutPolicyTypeIsInvalid() {
        final String json = ContentLoader.loadContentFromClasspath("lockout/invalid-lockout-policy-document.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, LockoutPolicyDocument.class));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage("invalid-type");
    }

}
