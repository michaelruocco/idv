package uk.co.idv.repository.mongo.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class NonLockingPolicyDocumentConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);

    private final LockoutPolicyDocumentConverter converter = new NonLockingPolicyDocumentConverter(recordAttemptStrategyFactory);

    @Test
    void shouldOnlySupportNonLockingLockoutType() {
        assertThat(converter.supportsType(NonLockingLockoutStateCalculator.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final UUID id = UUID.randomUUID();
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy(id);

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getId()).isEqualTo(id.toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getLockoutType()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateChannelIdOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getChannelId()).isEqualTo(level.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getActivityNames()).containsExactlyElementsOf(level.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.hardLockoutPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(document.getAliasTypes()).containsExactlyElementsOf(level.getAliasTypes());
    }

    @Test
    void shouldConvertToLockoutPolicyDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.nonLockingPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document).isInstanceOf(LockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateIdOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();
        final RecordAttemptStrategy strategy = new RecordNever();
        given(recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType())).willReturn(strategy);

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(strategy.getType());
    }

    @Test
    void shouldPopulateChannelIdOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getChannelId()).isEqualTo(document.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getActivityNames()).containsExactlyElementsOf(document.getActivityNames());
    }

    @Test
    void shouldPopulateAliasTypeOnLockoutLevelOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        final LockoutLevel level = policy.getLockoutLevel();
        assertThat(level.getAliasTypes()).containsExactlyElementsOf(document.getAliasTypes());
    }

    @Test
    void shouldConvertToLockoutPolicyWithNonLockingStateCalculator() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.nonLocking();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getStateCalculator()).isInstanceOf(NonLockingLockoutStateCalculator.class);
    }

}
