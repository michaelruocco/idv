package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.mobile;

import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MobilePinsentryConverterTest {

    private final VerificationResultsDocumentConverter resultsConverter = mock(VerificationResultsDocumentConverter.class);
    private final EligibilityDocumentConverter eligibilityConverter = mock(EligibilityDocumentConverter.class);

    private final MobilePinsentryDocumentConverter converter = MobilePinsentryDocumentConverter.builder()
            .resultsConverter(resultsConverter)
            .eligibilityConverter(eligibilityConverter)
            .build();

    @Test
    void shouldSupportMobilePinsentry() {
        final boolean supported = converter.supportsMethod(MobilePinsentry.NAME);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodName() {
        final boolean supported = converter.supportsMethod("other-method");

        assertThat(supported).isFalse();
    }

    @Test
    void shouldPopulateNameOnDocument() {
        final VerificationMethod method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getName()).isEqualTo(method.getName());
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final VerificationMethod method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateDurationOnDocument() {
        final VerificationMethod method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getDuration()).isEqualTo(method.getDuration().toMillis());
    }

    @Test
    void shouldPopulateEligibilityOnDocument() {
        final VerificationMethod method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);
        final EligibilityDocument eligibilityDocument = new EligibilityDocument();
        given(eligibilityConverter.toDocument(method.getEligibility())).willReturn(eligibilityDocument);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getEligibility()).isEqualTo(eligibilityDocument);
    }

    @Test
    void shouldPopulateResultsOnDocument() {
        final VerificationMethod method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getResults()).isEqualTo(resultDocuments);
    }

    @Test
    void shouldReturnMobilePinsentryDocument() {
        final MobilePinsentry method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document).isInstanceOf(MobilePinsentryDocument.class);
    }

    @Test
    void shouldPopulateFunctionOnDocument() {
        final MobilePinsentry method = new MobilePinsentryEligible(PinsentryFunction.IDENTIFY);
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final MobilePinsentryDocument document = (MobilePinsentryDocument) converter.toDocument(method);

        assertThat(document.getFunction()).isEqualTo(method.getFunction());
    }

    @Test
    void shouldConvertIneligibleDocumentIntoIneligibleMethod() {
        final VerificationMethodDocument document = new MobilePinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.ineligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(MobilePinsentryIneligible.class);
    }

    @Test
    void shouldConvertEligibleDocumentIntoEligibleMethod() {
        final VerificationMethodDocument document = new MobilePinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(MobilePinsentryEligible.class);
    }

    @Test
    void shouldPopulateResultsOnEligibleMethodIfDocumentIsEligible() {
        final VerificationMethodDocument document = new MobilePinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setResults(Collections.singleton(VerificationResultDocumentMother.successful()));
        final VerificationResults expectedResults = new DefaultVerificationResults();
        given(resultsConverter.toResults(document.getResults())).willReturn(expectedResults);

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method.getResults()).isEqualTo(expectedResults);
    }

    @Test
    void shouldPopulateFunctionOnEligibleMethodIfDocumentIsEligible() {
        final MobilePinsentryDocument document = new MobilePinsentryDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setFunction(PinsentryFunction.IDENTIFY);
        final VerificationResults expectedResults = new DefaultVerificationResults();
        given(resultsConverter.toResults(document.getResults())).willReturn(expectedResults);

        final MobilePinsentry method = (MobilePinsentry) converter.toMethod(document);

        assertThat(method.getFunction()).isEqualTo(document.getFunction());
    }

}
