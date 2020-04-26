package uk.co.idv.api.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMother;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecurringSoftLockoutPolicyAttributesConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final SoftLockIntervalDtosConverter softLockIntervalDtosConverter = mock(SoftLockIntervalDtosConverter.class);

    private final RecurringSoftLockoutPolicyAttributesConverter converter = RecurringSoftLockoutPolicyAttributesConverter.builder()
            .recordAttemptStrategyFactory(recordAttemptStrategyFactory)
            .softLockIntervalDtosConverter(softLockIntervalDtosConverter)
            .build();

    @Test
    void shouldSupportRecurringSoftLockoutType() {
        assertThat(converter.supports(RecurringSoftLockoutStateCalculator.TYPE)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherLockoutType() {
        assertThat(converter.supports("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getId()).isEqualTo(attributes.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getLevel()).isEqualTo(attributes.getLockoutLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.recurringSoftLock();
        final RecordAttemptStrategy strategy = mock(RecordAttemptStrategy.class);
        given(recordAttemptStrategyFactory.build(attributes.getRecordAttempts())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getRecordAttemptStrategy()).isEqualTo(strategy);
    }

    @Test
    void shouldPopulateStateCalculatorOnPolicy() {
        final LockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(attributes);

        assertThat(policy.getStateCalculator()).isInstanceOf(RecurringSoftLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateIntervalOnPolicy() {
        final RecurringSoftLockoutPolicyAttributes attributes = LockoutPolicyAttributesMother.recurringSoftLock();
        final SoftLockInterval interval = mock(SoftLockInterval.class);
        given(softLockIntervalDtosConverter.toInterval(attributes.getInterval())).willReturn(interval);

        final RecurringSoftLockoutPolicy policy = (RecurringSoftLockoutPolicy) converter.toPolicy(attributes);

        assertThat(policy.getInterval()).isEqualTo(interval);
    }

    @Test
    void shouldPopulateIdOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getId()).isEqualTo(policy.getId());
    }

    @Test
    void shouldPopulateLockoutLevelOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutLevel()).isEqualTo(policy.getLevel());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getRecordAttempts()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLockoutTypeOnAttributes() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyAttributes attributes = converter.toAttributes(policy);

        assertThat(attributes.getLockoutType()).isEqualTo(policy.getType());
    }

    @Test
    void shouldPopulateIntervalDtoOnAttributes() {
        final RecurringSoftLockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();
        final SoftLockIntervalDto dto = mock(SoftLockIntervalDto.class);
        given(softLockIntervalDtosConverter.toDto(policy.getInterval())).willReturn(dto);

        final RecurringSoftLockoutPolicyAttributes attributes = (RecurringSoftLockoutPolicyAttributes) converter.toAttributes(policy);

        assertThat(attributes.getInterval()).isEqualTo(dto);
    }

}
