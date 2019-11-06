package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersMother;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MaxAttemptsLockoutPolicyDocumentConverterTest {

    private final LockoutPolicyLookupDocumentConverter lookupConverter = mock(LockoutPolicyLookupDocumentConverter.class);

    private final LockoutPolicyDocumentConverter converter = new MaxAttemptsLockoutPolicyDocumentConverter(lookupConverter);

    @Test
    void shouldOnlySupportMaxAttemptsLockoutType() {
        assertThat(converter.supportsType(MaxAttemptsLockoutPolicyParameters.TYPE)).isTrue();
        assertThat(converter.supportsType("other-type")).isFalse();
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getId()).isEqualTo(parameters.getId().toString());
    }

    @Test
    void shouldPopulateLockoutTypeOnDocument() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getLockoutType()).isEqualTo(parameters.getLockoutType());
    }

    @Test
    void shouldPopulateRecordAttemptStrategyTypeOnDocument() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getRecordAttemptStrategyType()).isEqualTo(parameters.getRecordAttemptStrategyType());
    }

    @Test
    void shouldPopulateLookupDocumentsOnDocument() {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.maxAttempts();
        final Collection<LockoutPolicyLookupDocument> expectedLookups = Arrays.asList(
                new LockoutPolicyLookupDocument(),
                new LockoutPolicyLookupDocument()
        );
        given(lookupConverter.toLookupDocuments(parameters)).willReturn(expectedLookups);

        final LockoutPolicyDocument document = converter.toDocument(parameters);

        assertThat(document.getLookups()).isEqualTo(expectedLookups);
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
    void shouldPopulateChannelIdsOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();
        final Collection<String> channelIds = Collections.emptyList();
        given(lookupConverter.toChannelIds(document.getLookups())).willReturn(channelIds);

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getChannelIds()).isEqualTo(channelIds);
    }

    @Test
    void shouldPopulateActivityNamesOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();
        final Collection<String> activityNames = Collections.emptyList();
        given(lookupConverter.toActivityNames(document.getLookups())).willReturn(activityNames);

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getActivityNames()).isEqualTo(activityNames);
    }

    @Test
    void shouldPopulateAliasTypesOnLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();
        final Collection<String> aliasTypes = Collections.emptyList();
        given(lookupConverter.toAliasTypes(document.getLookups())).willReturn(aliasTypes);

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters.getAliasTypes()).isEqualTo(aliasTypes);
    }

    @Test
    void shouldConvertToMaxAttemptsLockoutPolicyParameters() {
        final LockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final LockoutPolicyParameters parameters = converter.toParameters(document);

        assertThat(parameters).isInstanceOf(MaxAttemptsLockoutPolicyParameters.class);
    }

    @Test
    void shouldPopulateMaxAttemptsOnLockoutPolicyParameters() {
        final MaxAttemptsLockoutPolicyDocument document = LockoutPolicyDocumentMother.maxAttempts();

        final MaxAttemptsLockoutPolicyParameters parameters = (MaxAttemptsLockoutPolicyParameters) converter.toParameters(document);

        assertThat(parameters.getMaxNumberOfAttempts()).isEqualTo(document.getMaxNumberOfAttempts());
    }

}
