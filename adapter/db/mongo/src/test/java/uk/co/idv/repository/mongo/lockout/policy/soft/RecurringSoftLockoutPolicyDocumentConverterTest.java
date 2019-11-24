package uk.co.idv.repository.mongo.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecurringSoftLockoutPolicyDocumentConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final SoftLockIntervalDocumentsConverter intervalDocumentsConverter = mock(SoftLockIntervalDocumentsConverter.class);

    private final LockoutPolicyDocumentConverter converter = new RecurringSoftLockoutPolicyDocumentConverter(
            recordAttemptStrategyFactory,
            intervalDocumentsConverter
    );

    @Test
    void shouldOnlySupportRecurringSoftLockoutType() {
        assertThat(converter.supportsType(RecurringSoftLockoutStateCalculator.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final UUID id = UUID.randomUUID();
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy(id);

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getId()).isEqualTo(id.toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getLockoutType()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateChannelIdOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getChannelId()).isEqualTo(level.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getActivityNames()).containsExactlyElementsOf(level.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getAliasTypes()).containsExactlyElementsOf(level.getAliasTypes());
    }

    @Test
    void shouldConvertToRecurringSoftLockoutPolicyDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document).isInstanceOf(RecurringSoftLockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateIntervalDocumentOnDocument() {
        final RecurringSoftLockoutPolicy policy = LockoutPolicyMother.recurringSoftLockoutPolicy();
        final SoftLockIntervalDocument intervalDocument = SoftLockIntervalDocumentMother.oneAttempt();
        given(intervalDocumentsConverter.toDocument(policy.getInterval())).willReturn(intervalDocument);

        final RecurringSoftLockoutPolicyDocument document = (RecurringSoftLockoutPolicyDocument) converter.toDocument(policy);

        assertThat(document.getInterval()).isEqualTo(intervalDocument);
    }

    @Test
    void shouldPopulateIdOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();
        final RecordAttemptStrategy strategy = new RecordEveryAttempt();
        given(recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(strategy.getType());
    }

    @Test
    void shouldPopulateChannelIdOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getChannelId()).isEqualTo(document.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getActivityNames()).containsExactlyElementsOf(document.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getAliasTypes()).containsExactlyElementsOf(document.getAliasTypes());
    }

    @Test
    void shouldConvertToLockoutPolicyWithSoftLockStateCalculator() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getStateCalculator()).isInstanceOf(RecurringSoftLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateIntervalOnLockoutPolicy() {
        final RecurringSoftLockoutPolicyDocument document = LockoutPolicyDocumentMother.recurringSoftLock();
        final SoftLockInterval interval = SoftLockIntervalMother.oneAttempt();
        given(intervalDocumentsConverter.toInterval(document.getInterval())).willReturn(interval);

        final RecurringSoftLockoutPolicy policy = (RecurringSoftLockoutPolicy) converter.toPolicy(document);

        assertThat(policy.getInterval()).isEqualTo(interval);
    }

}
