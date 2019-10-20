package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSms;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Builder
public class OneTimePasscodeSmsConverter implements VerificationMethodConverter {

    private final VerificationResultsConverter resultsConverter;
    private final EligibilityConverter eligibilityConverter;
    private final MobileNumberConverter mobileNumberConverter;
    private final PasscodeSettingsConverter passcodeSettingsConverter;

    @Override
    public String getSupportedMethodName() {
        return OneTimePasscodeSms.NAME;
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument methodDocument) {
        final OneTimePasscodeSmsDocument document = (OneTimePasscodeSmsDocument) methodDocument;
        if (document.isEligible()) {
            return toEligible(document);
        }
        return toIneligible(document);
    }

    private VerificationMethod toEligible(final OneTimePasscodeSmsDocument document) {
        return new OneTimePasscodeSmsEligible(
                passcodeSettingsConverter.toPasscodeSettings(document.getPasscodeSettings()),
                mobileNumberConverter.toMobileNumbers(document.getMobileNumbers()),
                resultsConverter.toResults(document.getResults())
        );
    }

    private VerificationMethod toIneligible(final OneTimePasscodeSmsDocument document) {
        return new OneTimePasscodeSmsEligible(
                passcodeSettingsConverter.toPasscodeSettings(document.getPasscodeSettings()),
                mobileNumberConverter.toMobileNumbers(document.getMobileNumbers())
        );
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final OneTimePasscodeSms oneTimePasscodeSms = (OneTimePasscodeSms) method;
        final OneTimePasscodeSmsDocument document = new OneTimePasscodeSmsDocument();
        VerificationMethodConverter.populateCommonFields(method, document);
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
            return mobileNumberConverter.toDocuments(eligible.getMobileNumbers());
        }
        return Collections.emptyList();
    }

}
