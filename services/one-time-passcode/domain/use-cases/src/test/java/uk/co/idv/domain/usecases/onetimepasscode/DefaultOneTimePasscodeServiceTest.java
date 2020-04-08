package uk.co.idv.domain.usecases.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultOneTimePasscodeServiceTest {

    private final OneTimePasscodeSender sender = mock(OneTimePasscodeSender.class);
    private final OneTimePasscodeVerificationLoader verificationLoader = mock(OneTimePasscodeVerificationLoader.class);
    private final OneTimePasscodeVerifier verifier = mock(OneTimePasscodeVerifier.class);

    private final OneTimePasscodeService service = DefaultOneTimePasscodeService.builder()
            .sender(sender)
            .verificationLoader(verificationLoader)
            .verifier(verifier)
            .build();

    @Test
    void shouldReturnVerificationFromSenderWhenSendingOtp() {
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        given(sender.send(request)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnVerificationFromSenderWhenResendingOtp() {
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        given(sender.send(request)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnVerificationFromVerifierWhenVerifyingOtp() {
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build();
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        given(verifier.verify(request)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.verify(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldLoadVerification() {
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        given(verificationLoader.load(expectedVerification.getId())).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.load(expectedVerification.getId());

        assertThat(verification).isEqualTo(expectedVerification);
    }

}
