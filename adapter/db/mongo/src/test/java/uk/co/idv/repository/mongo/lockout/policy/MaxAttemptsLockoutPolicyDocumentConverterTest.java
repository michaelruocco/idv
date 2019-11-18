package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsLockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsLockoutPolicyDocument;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MaxAttemptsLockoutPolicyDocumentConverterTest {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory = mock(RecordAttemptStrategyFactory.class);
    private final LockoutPolicyDocumentKeyConverter keyConverter = mock(LockoutPolicyDocumentKeyConverter.class);

    private final LockoutPolicyDocumentConverter converter = MaxAttemptsLockoutPolicyDocumentConverter.builder()
            .recordAttemptStrategyFactory(recordAttemptStrategyFactory)
            .keyConverter(keyConverter)
            .build();

    @Test
    void shouldOnlySupportMaxAttemptsLockoutType() {
        assertThat(converter.supportsType(MaxAttemptsLockoutStateCalculator.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final UUID id = UUID.randomUUID();
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy(id);

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getId()).isEqualTo(id.toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getLockoutType()).isEqualTo(policy.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(policy.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateKeyOnDocument() {
        final String expectedKey = "expected-key";
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();
        given(keyConverter.toKey(policy.getLockoutLevel())).willReturn(expectedKey);

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document.getKey()).isEqualTo(expectedKey);
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();

        final LockoutPolicyDocument document = converter.toDocument(policy);

        assertThat(document).isInstanceOf(MaxAttemptsLockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final LockoutPolicy policy = LockoutPolicyMother.maxAttemptsPolicy();

        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) converter.toDocument(policy);

        final MaxAttemptsLockoutStateCalculator stateCalculator = (MaxAttemptsLockoutStateCalculator) policy.getStateCalculator();
        assertThat(document.getMaxNumberOfAttempts()).isEqualTo(stateCalculator.getMaxNumberOfAttempts());
    }

    @Test
    void shouldPopulateIdOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicy() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();
        final RecordAttemptStrategy strategy = new RecordEveryAttempt();
        given(recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType())).willReturn(strategy);
        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getRecordAttemptStrategyType()).isEqualTo(strategy.getType());
    }

    @Test
    void shouldPopulateLockoutLevelOnLockoutPolicy() {
        final LockoutLevel expectedLevel = mock(LockoutLevel.class);
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();
        given(keyConverter.toLevel(document.getKey())).willReturn(expectedLevel);

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getLockoutLevel()).isEqualTo(expectedLevel);
    }

    @Test
    void shouldConvertToLockoutPolicyWithMaxAttemptsStateCalculator() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicy policy = converter.toPolicy(document);

        assertThat(policy.getStateCalculator()).isInstanceOf(MaxAttemptsLockoutStateCalculator.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnLockoutStateCalculator() {
        final MaxAttemptsLockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicy policy = converter.toPolicy(document);

        final MaxAttemptsLockoutStateCalculator stateCalculator = (MaxAttemptsLockoutStateCalculator) policy.getStateCalculator();
        assertThat(stateCalculator.getMaxNumberOfAttempts()).isEqualTo(document.getMaxNumberOfAttempts());
    }

}
