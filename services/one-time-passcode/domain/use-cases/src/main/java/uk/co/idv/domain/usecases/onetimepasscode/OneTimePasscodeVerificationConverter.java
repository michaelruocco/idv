package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.usecases.verificationcontext.result.RecordResultRequest;

@RequiredArgsConstructor
public class OneTimePasscodeVerificationConverter {

    private final TimeProvider timeProvider;

    public OneTimePasscodeVerificationConverter() {
        this(new CurrentTimeProvider());
    }

    public RecordResultRequest toRecordResultRequest(final OneTimePasscodeVerification verification) {
        return RecordResultRequest.builder()
                .contextId(verification.getContextId())
                .result(toResult(verification))
                .build();
    }

    private VerificationResult toResult(final OneTimePasscodeVerification verification) {
        return new OneTimePasscodeVerificationResult(
                verification.getId(),
                timeProvider.now(),
                verification.isSuccessful()
        );
    }

}
