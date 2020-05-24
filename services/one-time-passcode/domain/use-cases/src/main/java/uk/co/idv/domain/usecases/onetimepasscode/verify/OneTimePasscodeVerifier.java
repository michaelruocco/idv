package uk.co.idv.domain.usecases.onetimepasscode.verify;

import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationConverter;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.verificationcontext.result.RecordResultRequest;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

import java.util.Collection;

@Builder
public class OneTimePasscodeVerifier {

    @Builder.Default
    private final VerifyOneTimePasscodeRequestConverter requestConverter = new VerifyOneTimePasscodeRequestConverter();

    @Builder.Default
    private final OneTimePasscodeVerificationConverter verificationConverter = new OneTimePasscodeVerificationConverter();

    private final OneTimePasscodeVerificationLoader verificationLoader;
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
