package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyDocumentConverterDelegatorTest {

    private final LockoutPolicyDocumentConverter converter = mock(LockoutPolicyDocumentConverter.class);
    private final LockoutPolicyDocumentConverterDelegator delegator = new LockoutPolicyDocumentConverterDelegator(converter);

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicyDocument() {
        final LockoutPolicyDocument document = new LockoutPolicyDocument();

        final Throwable error = catchThrowable(() -> delegator.toPolicy(document));

        assertThat(error).isInstanceOf(LockoutTypeNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicy() {
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policy.getLockoutType()).willReturn("not-supported");

        final Throwable error = catchThrowable(() -> delegator.toDocument(policy));

        assertThat(error).isInstanceOf(LockoutTypeNotSupportedException.class);
        assertThat(error.getMessage()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldConvertLockoutPolicyDocument() {
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.maxAttemptsPolicy();
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        given(converter.supportsType(document.getLockoutType())).willReturn(true);
        given(converter.toPolicy(document)).willReturn(expectedPolicy);

        final LockoutPolicy policy = delegator.toPolicy(document);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldConvertLockoutPolicy() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();
        final LockoutPolicyDocument expectedDocument = new LockoutPolicyDocument();
        given(converter.supportsType(policy.getLockoutType())).willReturn(true);
        given(converter.toDocument(policy)).willReturn(expectedDocument);

        final LockoutPolicyDocument document = delegator.toDocument(policy);

        assertThat(document).isEqualTo(expectedDocument);
    }

}
