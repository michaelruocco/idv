package uk.co.idv.api.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HardLockoutPolicyAttributesConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);

    private final LockoutPolicyAttributesConverter converter = new HardLockoutPolicyAttributesConverter(recordAttemptStrategyFactory);

    @Test
    void shouldSupportHardLockoutType() {
        assertThat(converter.supports(HardLockoutStateCalculator.TYPE)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherLockoutType() {
        assertThat(converter.supports("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getId()).isEqualTo(attributes.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getLockoutLevel()).isEqualTo(attributes.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(attributes.getRecordAttempts())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getStateCalculator()).isInstanceOf(HardLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnPolicy() {
        final HardLockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.hardLock();

        final HardLockoutPolicy policy = (HardLockoutPolicy) converter.toPolicy(attributes);

        assertThat(policy.getMaxNumberOfAttempts()).isEqualTo(attributes.getMaxNumberOfAttempts());
    }

    @Test
    void shouldPopulateIdOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getId()).isEqualTo(policy.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutLevel()).isEqualTo(policy.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getRecordAttempts()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLockoutTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutType()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldPopulateMaxAttemptsOnAttributes() {
        final HardLockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final HardLockoutPolicyAttributes attributes = (HardLockoutPolicyAttributes) converter.toAttributes(policy);

        assertThat(attributes.getMaxNumberOfAttempts()).isEqualTo(policy.getMaxNumberOfAttempts());
    }

}
