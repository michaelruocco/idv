package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextResultRecorder;

import java.util.Collection;

@Builder
public class OneTimePasscodeVerifier {

    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final VerifyOneTimePasscodeRequestConverter requestConverter;
    private final OneTimePasscodeVerificationConverter verificationConverter;
    private final OneTimePasscodeVerificationDao dao;
    private final VerificationContextResultRecorder resultRecorder;

    public OneTimePasscodeVerification verify(final VerifyOneTimePasscodeRequest request) {
        final OneTimePasscodeVerification verification = verificationLoader.load(request.getId());
        return verify(request, verification);
    }

    private OneTimePasscodeVerification verify(final VerifyOneTimePasscodeRequest request,
                                               final OneTimePasscodeVerification verification) {
        final Collection<OneTimePasscodeVerificationAttempt> attempts = requestConverter.toAttempts(request);
        update(attempts, verification);
        recordResult(verification);
        return verification;
    }

    private void update(final Collection<OneTimePasscodeVerificationAttempt> attempts,
                        final OneTimePasscodeVerification verification) {
        verification.verify(attempts);
        dao.save(verification);
    }

    private void recordResult(final OneTimePasscodeVerification verification) {
        RecordResultRequest recordResultRequest = verificationConverter.toRecordResultRequest(verification);
        resultRecorder.recordResult(recordResultRequest);
    }

}
