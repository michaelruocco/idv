package uk.co.idv.api.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutPolicyAttributesConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final SoftLockIntervalDtosConverter softLockIntervalDtosConverter = mock(SoftLockIntervalDtosConverter.class);

    private final SoftLockoutPolicyAttributesConverter converter = SoftLockoutPolicyAttributesConverter.builder()
            .recordAttemptStrategyFactory(recordAttemptStrategyFactory)
            .softLockIntervalDtosConverter(softLockIntervalDtosConverter)
            .build();

    @Test
    void shouldSupportSoftLockoutType() {
        assertThat(converter.supports(SoftLockoutStateCalculator.TYPE)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherLockoutType() {
        assertThat(converter.supports("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getId()).isEqualTo(attributes.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getLevel()).isEqualTo(attributes.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.softLock();
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(attributes.getRecordAttempts())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getStateCalculator()).isInstanceOf(SoftLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateIntervalsOnPolicy() {
        final SoftLockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.softLock();
        final SoftLockIntervals intervals = mock(SoftLockIntervals.class);
        given(softLockIntervalDtosConverter.toIntervals(attributes.getIntervals())).willReturn(intervals);

        final SoftLockoutPolicy policy = (SoftLockoutPolicy) converter.toPolicy(attributes);

        assertThat(policy.getIntervals()).isEqualTo(intervals);
    }

    @Test
    void shouldPopulateIdOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getId()).isEqualTo(policy.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutLevel()).isEqualTo(policy.getLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getRecordAttempts()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLockoutTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutType()).isEqualTo(policy.getType());
    }

    @Test
    void shouldPopulateIntervalDtosOnAttributes() {
        final SoftLockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();
        final Collection<SoftLockIntervalDto> dtos = Collections.emptyList();
        given(softLockIntervalDtosConverter.toDtos(policy.getIntervals())).willReturn(dtos);

        final SoftLockoutPolicyAttributes attributes = (SoftLockoutPolicyAttributes) converter.toAttributes(policy);

        assertThat(attributes.getIntervals()).isEqualTo(dtos);
    }

}
