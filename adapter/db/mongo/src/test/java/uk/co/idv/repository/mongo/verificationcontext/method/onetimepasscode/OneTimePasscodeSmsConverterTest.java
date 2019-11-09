package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeSmsConverterTest {

    private final VerificationResultsDocumentConverter resultsConverter = mock(VerificationResultsDocumentConverter.class);
    private final EligibilityDocumentConverter eligibilityConverter = mock(EligibilityDocumentConverter.class);
    private final MobileNumbersDocumentConverter mobileNumbersConverter = mock(MobileNumbersDocumentConverter.class);
    private final PasscodeSettingsDocumentConverter passcodeSettingsConverter = mock(PasscodeSettingsDocumentConverter.class);

    private final OneTimePasscodeSmsDocumentConverter converter = OneTimePasscodeSmsDocumentConverter.builder()
            .resultsConverter(resultsConverter)
            .eligibilityConverter(eligibilityConverter)
            .mobileNumbersConverter(mobileNumbersConverter)
            .passcodeSettingsConverter(passcodeSettingsConverter)
            .build();

    @BeforeEach
    void setUp() {
        given(passcodeSettingsConverter.toDocument(any(PasscodeSettings.class))).willReturn(new PasscodeSettingsDocument());
    }
    @Test
    void shouldSupportOneTimePasscodeSms() {
        final boolean supported = converter.supportsMethod(OneTimePasscodeSms.NAME);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodName() {
        final boolean supported = converter.supportsMethod("other-method");

        assertThat(supported).isFalse();
    }

    @Test
    void shouldPopulateNameOnDocument() {
        final VerificationMethod method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getName()).isEqualTo(method.getName());
    }

    @Test
    void shouldPopulateMaxAttemptsOnDocument() {
        final VerificationMethod method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateDurationOnDocument() {
        final VerificationMethod method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getDuration()).isEqualTo(method.getDuration().toMillis());
    }

    @Test
    void shouldPopulateEligibilityOnDocument() {
        final VerificationMethod method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());
        final EligibilityDocument eligibilityDocument = new EligibilityDocument();
        given(eligibilityConverter.toDocument(method.getEligibility())).willReturn(eligibilityDocument);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getEligibility()).isEqualTo(eligibilityDocument);
    }

    @Test
    void shouldPopulateResultsOnDocument() {
        final VerificationMethod method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document.getResults()).isEqualTo(resultDocuments);
    }

    @Test
    void shouldReturnOneTimePasscodeSmsDocument() {
        final OneTimePasscodeSms method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());
        final Collection<VerificationResultDocument> resultDocuments = Collections.singleton(VerificationResultDocumentMother.successful());
        given(resultsConverter.toDocuments(method.getResults())).willReturn(resultDocuments);

        final VerificationMethodDocument document = converter.toDocument(method);

        assertThat(document).isInstanceOf(OneTimePasscodeSmsDocument.class);
    }

    @Test
    void shouldPopulatePasscodeSettingsOnDocumentIfEligible() {
        final OneTimePasscodeSmsEligible method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), Collections.emptyList());
        final PasscodeSettingsDocument settingsDocument = new PasscodeSettingsDocument();
        given(passcodeSettingsConverter.toDocument(method.getPasscodeSettings())).willReturn(settingsDocument);

        final OneTimePasscodeSmsDocument document = (OneTimePasscodeSmsDocument) converter.toDocument(method);

        assertThat(document.getPasscodeSettings()).isEqualTo(settingsDocument);
    }

    @Test
    void shouldPopulateMobileNumbersOnDocumentIfEligible() {
        final Collection<MobileNumber> mobileNumbers = Collections.emptyList();
        final OneTimePasscodeSms method = new OneTimePasscodeSmsEligible(new DefaultPasscodeSettings(), mobileNumbers);
        final Collection<MobileNumberDocument> mobileNumberDocuments = Collections.emptyList();
        given(mobileNumbersConverter.toDocuments(mobileNumbers)).willReturn(mobileNumberDocuments);
        final OneTimePasscodeSmsDocument document = (OneTimePasscodeSmsDocument) converter.toDocument(method);

        assertThat(document.getMobileNumbers()).isEqualTo(mobileNumberDocuments);
    }

    @Test
    void shouldPopulateEmptyMobileNumbersOnDocumentIfIneligible() {
        final OneTimePasscodeSms method = new OneTimePasscodeSmsIneligible();

        final OneTimePasscodeSmsDocument document = (OneTimePasscodeSmsDocument) converter.toDocument(method);

        assertThat(document.getMobileNumbers()).isEmpty();
    }

    @Test
    void shouldConvertIneligibleDocumentIntoIneligibleMethod() {
        final VerificationMethodDocument document = new OneTimePasscodeSmsDocument();
        document.setEligibility(EligibilityDocumentMother.ineligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(OneTimePasscodeSmsIneligible.class);
    }

    @Test
    void shouldConvertEligibleDocumentIntoEligibleMethod() {
        final VerificationMethodDocument document = new OneTimePasscodeSmsDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method).isInstanceOf(OneTimePasscodeSmsEligible.class);
    }

    @Test
    void shouldPopulateResultsOnEligibleMethodIfDocumentIsEligible() {
        final VerificationMethodDocument document = new OneTimePasscodeSmsDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        document.setResults(Collections.singleton(VerificationResultDocumentMother.successful()));
        final VerificationResults expectedResults = new DefaultVerificationResults();
        given(resultsConverter.toResults(document.getResults())).willReturn(expectedResults);

        final VerificationMethod method = converter.toMethod(document);

        assertThat(method.getResults()).isEqualTo(expectedResults);
    }

    @Test
    void shouldPopulatePasscodeSettingsOnEligibleMethodIfDocumentIsEligible() {
        final OneTimePasscodeSmsDocument document = new OneTimePasscodeSmsDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        final PasscodeSettingsDocument settingsDocument = PasscodeSettingsDocumentMother.build();
        document.setPasscodeSettings(settingsDocument);
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        given(passcodeSettingsConverter.toPasscodeSettings(settingsDocument)).willReturn(passcodeSettings);

        final OneTimePasscodeSmsEligible method = (OneTimePasscodeSmsEligible) converter.toMethod(document);

        assertThat(method.getPasscodeSettings()).isEqualTo(passcodeSettings);
    }

    @Test
    void shouldPopulateMobileNumbersOnEligibleMethodIfDocumentIsEligible() {
        final OneTimePasscodeSmsDocument document = new OneTimePasscodeSmsDocument();
        document.setEligibility(EligibilityDocumentMother.eligible());
        final Collection<MobileNumberDocument> mobileNumberDocuments = Collections.emptyList();
        document.setMobileNumbers(mobileNumberDocuments);
        final Collection<MobileNumber> expectedMobileNumbers = Collections.emptyList();
        given(mobileNumbersConverter.toMobileNumbers(document.getMobileNumbers())).willReturn(expectedMobileNumbers);

        final OneTimePasscodeSmsEligible method = (OneTimePasscodeSmsEligible) converter.toMethod(document);

        assertThat(method.getMobileNumbers()).containsExactlyElementsOf(expectedMobileNumbers);
    }

}
