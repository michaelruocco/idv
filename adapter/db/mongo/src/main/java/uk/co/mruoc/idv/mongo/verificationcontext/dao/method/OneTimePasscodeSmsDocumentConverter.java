package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Builder
public class OneTimePasscodeSmsDocumentConverter implements VerificationMethodDocumentConverter {

    private final VerificationResultsDocumentConverter resultsConverter;
    private final EligibilityDocumentConverter eligibilityConverter;
    private final MobileNumbersDocumentConverter mobileNumbersConverter;
    private final PasscodeSettingsDocumentConverter passcodeSettingsConverter;

    @Override
    public boolean supportsMethod(final String methodName) {
        return OneTimePasscodeSms.NAME.equals(methodName);
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument methodDocument) {
        final OneTimePasscodeSmsDocument document = (OneTimePasscodeSmsDocument) methodDocument;
        if (document.isEligible()) {
            return toEligible(document);
        }
        return toIneligible();
    }

    private VerificationMethod toEligible(final OneTimePasscodeSmsDocument document) {
        return new OneTimePasscodeSmsEligible(
                passcodeSettingsConverter.toPasscodeSettings(document.getPasscodeSettings()),
                mobileNumbersConverter.toMobileNumbers(document.getMobileNumbers()),
                resultsConverter.toResults(document.getResults())
        );
    }

    private VerificationMethod toIneligible() {
        return new OneTimePasscodeSmsIneligible();
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final OneTimePasscodeSms oneTimePasscodeSms = (OneTimePasscodeSms) method;
        final OneTimePasscodeSmsDocument document = new OneTimePasscodeSmsDocument();
        VerificationMethodDocumentConverter.populateCommonFields(method, document);
        document.setEligibility(eligibilityConverter.toDocument(oneTimePasscodeSms.getEligibility()));
        document.setResults(resultsConverter.toDocuments(oneTimePasscodeSms.getResults()));
        document.setMobileNumbers(extractMobileNumbers(oneTimePasscodeSms));
        extractPasscodeSettings(oneTimePasscodeSms).ifPresent(document::setPasscodeSettings);
        return document;
    }

    private Optional<PasscodeSettingsDocument> extractPasscodeSettings(final OneTimePasscodeSms oneTimePasscodeSms) {
        if (oneTimePasscodeSms.isEligible()) {
            final OneTimePasscodeSmsEligible eligible = (OneTimePasscodeSmsEligible) oneTimePasscodeSms;
            return Optional.of(passcodeSettingsConverter.toDocument(eligible.getPasscodeSettings()));
        }
        return Optional.empty();
    }

    private Collection<MobileNumberDocument> extractMobileNumbers(final OneTimePasscodeSms oneTimePasscodeSms) {
        if (oneTimePasscodeSms.isEligible()) {
            final OneTimePasscodeSmsEligible eligible = (OneTimePasscodeSmsEligible) oneTimePasscodeSms;
            return mobileNumbersConverter.toDocuments(eligible.getMobileNumbers());
        }
        return Collections.emptyList();
    }

}
