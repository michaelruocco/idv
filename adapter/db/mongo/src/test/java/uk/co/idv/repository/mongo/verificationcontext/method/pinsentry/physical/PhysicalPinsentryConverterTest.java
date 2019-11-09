package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhysicalPinsentryConverterTest {

    private final VerificationResultsDocumentConverter resultsConverter = mock(VerificationResultsDocumentConverter.class);
    private final EligibilityDocumentConverter eligibilityConverter = mock(EligibilityDocumentConverter.class);
    private final CardNumbersDocumentConverter cardNumbersConverter = mock(CardNumbersDocumentConverter.class);

    private final PhysicalPinsentryDocumentConverter converter = PhysicalPinsentryDocumentConverter.builder()
            .resultsConverter(resultsConverter)
            .eligibilityConverter(eligibilityConverter)
            .cardNumbersConverter(cardNumbersConverter)
            .build();

    @Test
    void shouldSupportPhysicalPinsentry() {
        final boolean supported = converter.supportsMethod(PhysicalPinsentry.NAME);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodName() {
        final boolean supported = converter.supportsMethod("other-method");

        assertThat(supported).isFalse();
    }

    @Test
    void shouldPopulateNameOnDocument() {
        final VerificationMethod method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getName()).isEqualTo(method.getName());
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final VerificationMethod method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateDurationOnDocument() {
        final VerificationMethod method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getDuration()).isEqualTo(method.getDuration().toMillis());
    }

    @Test
    void shouldPopulateEligibilityOnDocument() {
        final VerificationMethod method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());
        final EligibilityDocument eligibilityDocument = new EligibilityDocument();
        given(eligibilityConverter.toDocument(method.getEligibility())).willReturn(eligibilityDocument);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getEligibility()).isEqualTo(eligibilityDocument);
    }

    @Test
    void shouldPopulateResultsOnDocument() {
        final VerificationMethod method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getResults()).isEqualTo(resultDocuments);
    }

    @Test
    void shouldReturnPhysicalPinsentryDocument() {
        final PhysicalPinsentry method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document).isInstanceOf(PhysicalPinsentryDocument.class);
    }

    @Test
    void shouldPopulateCardNumbersOnDocumentIfEligible() {
        final Collection<CardNumber> cardNumbers = Collections.emptyList();
        final PhysicalPinsentry method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, cardNumbers);
        final Collection<CardNumberDocument> cardNumberDocuments = Collections.emptyList();
        given(cardNumbersConverter.toDocuments(cardNumbers)).willReturn(cardNumberDocuments);
        final PhysicalPinsentryDocument document = (PhysicalPinsentryDocument) converter.toDocument(method);

        assertThat(document.getCardNumbers()).isEqualTo(cardNumberDocuments);
    }

    @Test
    void shouldPopulateEmptyCardNumbersOnDocumentIfIneligible() {
        final PhysicalPinsentry method = new PhysicalPinsentryIneligible(new FakeIneligible(), PinsentryFunction.IDENTIFY);

        final PhysicalPinsentryDocument document = (PhysicalPinsentryDocument) converter.toDocument(method);

        assertThat(document.getCardNumbers()).isEmpty();
    }

    @Test
    void shouldPopulateFunctionOnDocument() {
        final PhysicalPinsentry method = new PhysicalPinsentryEligible(PinsentryFunction.IDENTIFY, Collections.emptyList());
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final PhysicalPinsentryDocument document = (PhysicalPinsentryDocument) converter.toDocument(method);

        assertThat(document.getFunction()).isEqualTo(method.getFunction());
    }

    @Test
    void shouldConvertIneligibleDocumentIntoIneligibleMethod() {
        final VerificationMethodDocument document = new PhysicalPinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.ineligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(PhysicalPinsentryIneligible.class);
    }

    @Test
    void shouldConvertEligibleDocumentIntoEligibleMethod() {
        final VerificationMethodDocument document = new PhysicalPinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(PhysicalPinsentryEligible.class);
    }

    @Test
    void shouldPopulateResultsOnEligibleMethodIfDocumentIsEligible() {
        final VerificationMethodDocument document = new PhysicalPinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setResults(Collections.singleton(VerificationResultDocumentMother.successful()));
        final VerificationResults expectedResults = new DefaultVerificationResults();
        given(resultsConverter.toResults(document.getResults())).willReturn(expectedResults);

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method.getResults()).isEqualTo(expectedResults);
    }

    @Test
    void shouldPopulateFunctionOnEligibleMethodIfDocumentIsEligible() {
        final PhysicalPinsentryDocument document = new PhysicalPinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setFunction(PinsentryFunction.IDENTIFY);

        final PhysicalPinsentry method = (PhysicalPinsentry) converter.toMethod(document);

        assertThat(method.getFunction()).isEqualTo(document.getFunction());
    }

    @Test
    void shouldPopulateCardNumbersOnEligibleMethodIfDocumentIsEligible() {
        final PhysicalPinsentryDocument document = new PhysicalPinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        final Collection<CardNumberDocument> cardNumberDocuments = Collections.emptyList();
        document.setCardNumbers(cardNumberDocuments);
        final Collection<CardNumber> expectedCardNumbers = Collections.emptyList();
        given(cardNumbersConverter.toCardNumbers(document.getCardNumbers())).willReturn(expectedCardNumbers);

        final PhysicalPinsentryEligible method = (PhysicalPinsentryEligible) converter.toMethod(document);

        assertThat(method.getCardNumbers()).containsExactlyElementsOf(expectedCardNumbers);
    }

}
