package uk.co.idv.repository.mongo.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParametersMother;
import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsAliasLevelLockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsLockoutPolicyDocument;
import uk.co.idv.domain.entities.lockout.policy.AliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsAliasLevelLockoutPolicyParameters;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MaxAttemptsAliasLevelLockoutPolicyDocumentConverterTest {

    private final LockoutPolicyDocumentConverter converter = new MaxAttemptsAliasLevelLockoutPolicyDocumentConverter();

    @Test
    void shouldOnlySupportMaxAttemptsLockoutType() {
        assertThat(converter.supportsType(MaxAttemptsAliasLevelLockoutPolicyParameters.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getId()).isEqualTo(parameters.getId().toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getLockoutType()).isEqualTo(parameters.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(parameters.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateKeyOnDocument() {
        final DefaultLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getKey()).isEqualTo("fake-channel*fake-activity*fake-alias-type");
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyDocument() {
        final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document).isInstanceOf(MaxAttemptsLockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) converter.toDocument(parameters);

        assertThat(document.getMaxNumberOfAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

    @Test
    void shouldPopulateIdOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getRecordAttemptStrategyType()).isEqualTo(document.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateChannelIdOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getChannelId()).isEqualTo("fake-channel");
    }

    @Test
    void shouldPopulateActivityNameOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getActivityName()).isEqualTo("fake-activity");
    }

    @Test
    void shouldPopulateAliasTypeOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AliasLevelLockoutPolicyParameters parameters = (AliasLevelLockoutPolicyParameters) converter.toParameters(document);

        assertThat(parameters.getAliasType()).isEqualTo("fake-alias");
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters).isInstanceOf(MaxAttemptsAliasLevelLockoutPolicyParameters.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnLockoutPolicyParameters() {
        final MaxAttemptsLockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final MaxAttemptsAliasLevelLockoutPolicyParameters parameters = (MaxAttemptsAliasLevelLockoutPolicyParameters) converter.toParameters(document);

        assertThat(parameters.getMaxNumberOfAttempts()).isEqualTo(document.getMaxNumberOfAttempts());
    }

}
