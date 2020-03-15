package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;

@RequiredArgsConstructor
public class OneTimePasscodeVerificationConverter {

    private final TimeGenerator timeGenerator;

    public RecordResultRequest toRecordResultRequest(final OneTimePasscodeVerification verification) {
        return RecordResultRequest.builder()
                .contextId(verification.getContextId())
                .result(toResult(verification))
                .build();
    }

    private VerificationResult toResult(final OneTimePasscodeVerification verification) {
        return new OneTimePasscodeVerificationResult(
                verification.getId(),
                timeGenerator.now(),
                verification.isSuccessful()
        );
    }

}
