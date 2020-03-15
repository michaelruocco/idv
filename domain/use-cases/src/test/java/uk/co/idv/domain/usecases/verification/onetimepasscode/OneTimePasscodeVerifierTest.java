package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttemptMother;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequestMother;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextResultRecorder;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OneTimePasscodeVerifierTest {

    private final OneTimePasscodeVerificationLoader verificationLoader = mock(OneTimePasscodeVerificationLoader.class);
    private final VerifyOneTimePasscodeRequestConverter requestConverter = mock(VerifyOneTimePasscodeRequestConverter.class);
    private final OneTimePasscodeVerificationConverter verificationConverter = mock(OneTimePasscodeVerificationConverter.class);
    private final OneTimePasscodeVerificationDao dao = mock(OneTimePasscodeVerificationDao.class);
    private final VerificationContextResultRecorder resultRecorder = mock(VerificationContextResultRecorder.class);

    private final OneTimePasscodeVerifier verifier = OneTimePasscodeVerifier.builder()
            .verificationLoader(verificationLoader)
            .requestConverter(requestConverter)
            .verificationConverter(verificationConverter)
            .dao(dao)
            .resultRecorder(resultRecorder)
            .build();

    @Test
    void shouldLoadVerification() {
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(request.getId())).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = verifier.verify(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldUpdateAttemptsOnVerificationAndSave() {
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification verification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(request.getId())).willReturn(verification);
        final Collection<OneTimePasscodeVerificationAttempt> attempts = Collections.singleton(OneTimePasscodeVerificationAttemptMother.attempt());
        given(requestConverter.toAttempts(request)).willReturn(attempts);

        verifier.verify(request);

        final InOrder inOrder = Mockito.inOrder(verification, dao);
        inOrder.verify(verification).verify(attempts);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldRecordResult() {
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification verification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(request.getId())).willReturn(verification);
        final RecordResultRequest recordResultRequest = RecordResultRequestMother.build();
        given(verificationConverter.toRecordResultRequest(verification)).willReturn(recordResultRequest);

        verifier.verify(request);

        verify(resultRecorder).recordResult(recordResultRequest);
    }

}
