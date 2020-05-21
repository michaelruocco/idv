package uk.co.idv.app.rest.verification.onetimepasscode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.onetimepasscode.OneTimePasscodeVerificationDocument;
import uk.co.idv.api.onetimepasscode.ResendOneTimePasscodeRequestDocument;
import uk.co.idv.api.onetimepasscode.SendOneTimePasscodeRequestDocument;
import uk.co.idv.api.onetimepasscode.VerifyOneTimePasscodeRequestDocument;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.usecases.onetimepasscode.FakeOneTimePasscodeService;
import uk.co.idv.domain.usecases.onetimepasscode.send.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.send.ResendOneTimePasscodeRequestMother;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequestMother;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequestMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeControllerTest {

    private final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
    private final FakeOneTimePasscodeService service = new FakeOneTimePasscodeService();

    private final OneTimePasscodeController controller = new OneTimePasscodeController(service);

    @BeforeEach
    void setUp() {
        service.setVerificationToReturn(verification);
    }

    @Test
    void shouldPassSendOneTimePasscodeRequestToService() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildSendOneTimePasscodeRequestDocument();

        controller.sendOtp(requestDocument);

        final SendOneTimePasscodeRequest request = service.getLastCreateRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedVerification() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildSendOneTimePasscodeRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.sendOtp(requestDocument);

        final OneTimePasscodeVerificationDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
        assertThat(responseDocument.getAttributes()).isEqualTo(verification);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildSendOneTimePasscodeRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.sendOtp(requestDocument);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnLocationHeader() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildSendOneTimePasscodeRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.sendOtp(requestDocument);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/one-time-passcode-verifications/%s", verification.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldPassProvidedIdToService() {
        final UUID id = UUID.randomUUID();

        controller.getVerification(id);

        final UUID expectedId = service.getLastLoadedId();
        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    void shouldReturnVerification() {
        final UUID id = UUID.randomUUID();

        final OneTimePasscodeVerificationDocument document = controller.getVerification(id);

        assertThat(document.getAttributes()).isEqualTo(verification);
    }

    @Test
    void shouldPassResendOneTimePasscodeRequestToService() {
        final ResendOneTimePasscodeRequestDocument requestDocument = buildResendOneTimePasscodeRequestDocument();

        controller.resendOtp(requestDocument);

        final ResendOneTimePasscodeRequest request = service.getLastUpdateRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnUpdatedVerificationAfterResend() {
        final ResendOneTimePasscodeRequestDocument requestDocument = buildResendOneTimePasscodeRequestDocument();

        final OneTimePasscodeVerificationDocument response = controller.resendOtp(requestDocument);

        assertThat(response.getAttributes()).isEqualTo(verification);
    }

    @Test
    void shouldPassVerifyOneTimePasscodeRequestToService() {
        final VerifyOneTimePasscodeRequestDocument requestDocument = buildVerifyOneTimePasscodeRequestDocument();

        controller.verifyOtp(requestDocument);

        final VerifyOneTimePasscodeRequest request = service.getLastVerifyRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnUpdatedVerificationAfterVerify() {
        final VerifyOneTimePasscodeRequestDocument requestDocument = buildVerifyOneTimePasscodeRequestDocument();

        final OneTimePasscodeVerificationDocument response = controller.verifyOtp(requestDocument);

        assertThat(response.getAttributes()).isEqualTo(verification);
    }

    private static SendOneTimePasscodeRequestDocument buildSendOneTimePasscodeRequestDocument() {
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequestMother.build();
        return new SendOneTimePasscodeRequestDocument(request);
    }

    private static ResendOneTimePasscodeRequestDocument buildResendOneTimePasscodeRequestDocument() {
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequestMother.build();
        return new ResendOneTimePasscodeRequestDocument(request);
    }

    private static VerifyOneTimePasscodeRequestDocument buildVerifyOneTimePasscodeRequestDocument() {
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build();
        return new VerifyOneTimePasscodeRequestDocument(request);
    }

}
