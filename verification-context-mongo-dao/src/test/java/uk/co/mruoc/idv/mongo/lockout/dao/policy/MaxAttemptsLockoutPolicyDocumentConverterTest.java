package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MaxAttemptsLockoutPolicyDocumentConverterTest {

    private final LockoutPolicyDocumentConverter converter = new MaxAttemptsLockoutPolicyDocumentConverter();

    @Test
    void shouldOnlySupportMaxAttemptsLockoutType() {
        assertThat(converter.supportsType(MaxAttemptsLockoutPolicyParameters.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final AbstractLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getId()).isEqualTo(parameters.getId().toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final AbstractLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getLockoutType()).isEqualTo(parameters.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final AbstractLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(parameters.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLookupDocumentsOnDocument() {
        final AbstractLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getLookups()).containsExactly(new LockoutLookupDocument(
                parameters.getChannelIds().iterator().next(),
                parameters.getActivityNames().iterator().next(),
                parameters.getAliasTypes().iterator().next()
        ));
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyDocument() {
        final MaxAttemptsLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document).isInstanceOf(MaxAttemptsLockoutPolicyDocument.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final MaxAttemptsLockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) converter.toDocument(parameters);

        assertThat(document.getMaxNumberOfAttempts()).isEqualTo(parameters.getMaxNumberOfAttempts());
    }

    @Test
    void shouldPopulateIdOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateLockoutTypeOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getLockoutType()).isEqualTo(document.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getRecordAttemptStrategyType()).isEqualTo(document.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateChannelIdsOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        final LockoutLookupDocument lookupDocument = document.getLookup(0);
        assertThat(parameters.getChannelIds()).containsExactly(lookupDocument.getChannelId());
    }

    @Test
    void shouldPopulateActivityNamesOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        final LockoutLookupDocument lookupDocument = document.getLookup(0);
        assertThat(parameters.getActivityNames()).containsExactly(lookupDocument.getActivityName());
    }

    @Test
    void shouldPopulateAliasTypesOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        final LockoutLookupDocument lookupDocument = document.getLookup(0);
        assertThat(parameters.getAliasTypes()).containsExactly(lookupDocument.getAliasType());
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final AbstractLockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters).isInstanceOf(MaxAttemptsLockoutPolicyParameters.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnLockoutPolicyParameters() {
        final MaxAttemptsLockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final MaxAttemptsLockoutPolicyParameters parameters = (MaxAttemptsLockoutPolicyParameters) converter.toParameters(document);

        assertThat(parameters.getMaxNumberOfAttempts()).isEqualTo(document.getMaxNumberOfAttempts());
    }

}
