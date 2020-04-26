package uk.co.idv.api.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutPolicyAttributesConverterDelegatorTest {

    private final LockoutPolicyAttributesConverter converter = mock(LockoutPolicyAttributesConverter.class);
    private final LockoutPolicyAttributesConverterDelegator delegator = new LockoutPolicyAttributesConverterDelegator(converter);

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicyAttributes() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();

        final Throwable error = catchThrowable(() -> delegator.toPolicy(attributes));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage(attributes.getType());
    }

    @Test
    void shouldThrowExceptionIfNoConverterSupportingLockoutPolicy() {
        final LockoutPolicy policy = mock(LockoutPolicy.class);
        given(policy.getType()).willReturn("not-supported");

        final Throwable error = catchThrowable(() -> delegator.toAttributes(policy));

        assertThat(error)
                .isInstanceOf(LockoutTypeNotSupportedException.class)
                .hasMessage(policy.getType());
    }

    @Test
    void shouldConvertLockoutPolicyAttributes() {
        final LockoutPolicy expectedPolicy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        given(converter.supports(attributes.getType())).willReturn(true);
        given(converter.toPolicy(attributes)).willReturn(expectedPolicy);

        final LockoutPolicy policy = delegator.toPolicy(attributes);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldConvertLockoutPolicy() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();
        final LockoutPolicyAttributes expectedAttributes = LockoutPolicyAttributesMother.hardLock();
        given(converter.supports(policy.getType())).willReturn(true);
        given(converter.toAttributes(policy)).willReturn(expectedAttributes);

        final LockoutPolicyAttributes attributes = delegator.toAttributes(policy);

        assertThat(attributes).isEqualTo(expectedAttributes);
    }

}
