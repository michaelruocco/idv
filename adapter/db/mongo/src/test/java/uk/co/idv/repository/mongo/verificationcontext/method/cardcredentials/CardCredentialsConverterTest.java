package uk.co.idv.repository.mongo.verificationcontext.method.cardcredentials;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentials;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CardCredentialsConverterTest {

    private final VerificationResultsDocumentConverter resultsConverter = mock(VerificationResultsDocumentConverter.class);
    private final EligibilityDocumentConverter eligibilityConverter = mock(EligibilityDocumentConverter.class);

    private final CardCredentialsDocumentConverter converter = CardCredentialsDocumentConverter.builder()
            .resultsConverter(resultsConverter)
            .eligibilityConverter(eligibilityConverter)
            .build();

    @Test
    void shouldSupportCardCredentials() {
        final boolean supported = converter.supportsMethod(CardCredentials.NAME);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodName() {
        final boolean supported = converter.supportsMethod("other-method");

        assertThat(supported).isFalse();
    }

    @Test
    void shouldPopulateNameOnDocument() {
        final VerificationMethod method = new PushNotificationEligible();

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getName()).isEqualTo(method.getName());
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final VerificationMethod method = new PushNotificationEligible();

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateDurationOnDocument() {
        final VerificationMethod method = new PushNotificationEligible();

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getDuration()).isEqualTo(method.getDuration().toMillis());
    }

    @Test
    void shouldPopulateEligibilityOnDocument() {
        final VerificationMethod method = new PushNotificationEligible();
        final EligibilityDocument eligibilityDocument = new EligibilityDocument();
        given(eligibilityConverter.toDocument(method.getEligibility())).willReturn(eligibilityDocument);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getEligibility()).isEqualTo(eligibilityDocument);
    }

    @Test
    void shouldPopulateResultsOnDocument() {
        final VerificationMethod method = new PushNotificationEligible();
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getResults()).isEqualTo(resultDocuments);
    }

    @Test
    void shouldConvertIneligibleDocumentIntoIneligibleMethod() {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        document.setEligibility(EligibilityDocumentMother.ineligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(CardCredentialsIneligible.class);
    }

    @Test
    void shouldConvertEligibleDocumentIntoEligibleMethod() {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(CardCredentialsEligible.class);
    }

    @Test
    void shouldPopulateResultsOnEligibleMethodIfDocumentIsEligible() {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setResults(Collections.singleton(VerificationResultDocumentMother.successful()));
        final VerificationResults expectedResults = new DefaultVerificationResults();
        given(resultsConverter.toResults(document.getResults())).willReturn(expectedResults);

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method.getResults()).isEqualTo(expectedResults);
    }

}