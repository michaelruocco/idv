package uk.co.idv.api.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NonLockingPolicyAttributesConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);

    private final LockoutPolicyAttributesConverter converter = new NonLockingPolicyAttributesConverter(recordAttemptStrategyFactory);

    @Test
    void shouldSupportHardLockoutType() {
        assertThat(converter.supports(NonLockingLockoutStateCalculator.TYPE)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherLockoutType() {
        assertThat(converter.supports("other-type")).isFalse();
    }

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getLevel()).isEqualTo(parameters.getLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final LockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.nonLocking();
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(parameters.getRecordAttempts())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutPolicyAttributes parameters = LockoutPolicyAttributesMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(parameters);

        assertThat(policy.getStateCalculator()).isInstanceOf(NonLockingLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateIdOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getId()).isEqualTo(policy.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLevel()).isEqualTo(policy.getLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getRecordAttempts()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLockoutTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getType()).isEqualTo(policy.getType());
    }

}
