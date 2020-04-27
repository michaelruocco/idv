package uk.co.idv.api.lockout.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.ApiVerificationContextObjectMapperFactory;
import uk.co.idv.domain.entities.lockout.assertion.LockoutAssertions;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class LockoutPolicyDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializeHardLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicy attributes = document.getAttributes();
        final LockoutPolicy expectedAttributes = LockoutPolicyMother.hardLockoutPolicy();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "level");
        LockoutAssertions.assertThat(attributes.getLevel()).isEqualTo(expectedAttributes.getLevel());
    }

    @Test
    void shouldDeserializeNonLockingPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/non-locking/non-locking-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicy attributes = document.getAttributes();
        final LockoutPolicy expectedAttributes = LockoutPolicyMother.nonLockingPolicy();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "level");
        LockoutAssertions.assertThat(attributes.getLevel()).isEqualTo(expectedAttributes.getLevel());
    }

    @Test
    void shouldDeserializeSoftLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/soft/soft-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicy attributes = document.getAttributes();
        final LockoutPolicy expectedAttributes = LockoutPolicyMother.softLockoutPolicy();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "level");
        LockoutAssertions.assertThat(attributes.getLevel()).isEqualTo(expectedAttributes.getLevel());
    }

    @Test
    void shouldDeserializeRecurringSoftLockoutPolicy() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/soft/recurring-soft-lockout-policy-document.json");

        final LockoutPolicyDocument document = MAPPER.readValue(json, LockoutPolicyDocument.class);

        final LockoutPolicy attributes = document.getAttributes();
        final LockoutPolicy expectedAttributes = LockoutPolicyMother.recurringSoftLockoutPolicy();
        assertThat(attributes).isEqualToIgnoringGivenFields(expectedAttributes, "level");
        LockoutAssertions.assertThat(attributes.getLevel()).isEqualTo(expectedAttributes.getLevel());
    }

    @Test
    void shouldThrowExceptionIfStateCalculatorTypeIsInvalid() {
        final String json = ContentLoader.loadContentFromClasspath("lockout/invalid-lockout-policy-document.json");

        final Throwable error = catchThrowable(() -> MAPPER.readValue(json, LockoutPolicyDocument.class));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage("invalid-type");
    }

}
