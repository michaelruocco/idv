package uk.co.idv.repository.mongo.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentMother;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutPolicyDocumentConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final SoftLockIntervalDocumentsConverter intervalDocumentsConverter = mock(SoftLockIntervalDocumentsConverter.class);

    private final LockoutPolicyDocumentConverter converter = new SoftLockoutPolicyDocumentConverter(
            recordAttemptStrategyFactory,
            intervalDocumentsConverter
    );

    @Test
    void shouldOnlySupportSoftLockoutType() {
        assertThat(converter.supportsType(SoftLockoutStateCalculator.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final UUID id = UUID.randomUUID();
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy(id);

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getId()).isEqualTo(id.toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getLockoutType()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateChannelIdOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getChannelId()).isEqualTo(level.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getActivityNames()).containsExactlyElementsOf(level.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getAliasTypes()).containsExactlyElementsOf(level.getAliasTypes());
    }

    @Test
    void shouldConvertToSoftLockoutPolicyDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document).isInstanceOf(SoftLockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateIntervalDocumentsOnDocument() {
        final SoftLockoutPolicy policy = LockoutPolicyMother.softLockoutPolicy();
        final Collection<SoftLockIntervalDocument> intervalDocuments = Collections.singleton(SoftLockIntervalDocumentMother.oneAttempt());
        given(intervalDocumentsConverter.toDocuments(policy.getIntervals())).willReturn(intervalDocuments);

        final SoftLockoutPolicyDocument document = (SoftLockoutPolicyDocument) converter.toDocument(policy);

        assertThat(document.getIntervals()).isEqualTo(intervalDocuments);
    }

    @Test
    void shouldPopulateIdOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();
        final RecordAttemptStrategy strategy = new RecordEveryAttempt();
        given(recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(strategy.getType());
    }

    @Test
    void shouldPopulateChannelIdOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getChannelId()).isEqualTo(document.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getActivityNames()).containsExactlyElementsOf(document.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getAliasTypes()).containsExactlyElementsOf(document.getAliasTypes());
    }

    @Test
    void shouldConvertToLockoutPolicyWithSoftLockStateCalculator() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getStateCalculator()).isInstanceOf(SoftLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateIntervalsOnLockoutPolicy() {
        final SoftLockoutPolicyDocument document = LockoutPolicyDocumentMother.softLock();
        final SoftLockIntervals intervals = SoftLockIntervalMother.intervals();
        given(intervalDocumentsConverter.toIntervals(document.getIntervals())).willReturn(intervals);

        final SoftLockoutPolicy policy = (SoftLockoutPolicy) converter.toPolicy(document);

        assertThat(policy.getIntervals()).isEqualTo(intervals);
    }

}
