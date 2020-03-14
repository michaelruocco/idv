package uk.co.idv.app.rest.verification.onetimepasscode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.verification.onetimepasscode.SendOneTimePasscodeRequestDocument;
import uk.co.idv.api.verification.onetimepasscode.OneTimePasscodeVerificationDocument;
import uk.co.idv.api.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequestDocument;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequestMother;
import uk.co.idv.domain.usecases.verification.onetimepasscode.FakeOneTimePasscodeService;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequestMother;

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
    void shouldPassCreateContextRequestToService() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildCreateVerificationRequestDocument();

        controller.createVerification(requestDocument);

        final SendOneTimePasscodeRequest request = service.getLastCreateRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedContext() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildCreateVerificationRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.createVerification(requestDocument);

        final OneTimePasscodeVerificationDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
        assertThat(responseDocument.getAttributes()).isEqualTo(verification);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildCreateVerificationRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.createVerification(requestDocument);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnLocationHeader() {
        final SendOneTimePasscodeRequestDocument requestDocument = buildCreateVerificationRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.createVerification(requestDocument);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/oneTimePasscodeVerifications/%s", verification.getId());
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
    void shouldPassUpdateContextRequestToService() {
        final UpdateOneTimePasscodeVerificationRequestDocument requestDocument = buildUpdateVerificationRequestDocument();

        controller.updateVerification(requestDocument);

        final UpdateOneTimePasscodeVerificationRequest request = service.getLastUpdateRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnUpdateContext() {
        final UpdateOneTimePasscodeVerificationRequestDocument requestDocument = buildUpdateVerificationRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.updateVerification(requestDocument);

        final OneTimePasscodeVerificationDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
        assertThat(responseDocument.getAttributes()).isEqualTo(verification);
    }

    @Test
    void shouldReturnOkStatus() {
        final UpdateOneTimePasscodeVerificationRequestDocument requestDocument = buildUpdateVerificationRequestDocument();

        final ResponseEntity<OneTimePasscodeVerificationDocument> response = controller.updateVerification(requestDocument);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private static SendOneTimePasscodeRequestDocument buildCreateVerificationRequestDocument() {
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequestMother.build();
        return new SendOneTimePasscodeRequestDocument(request);
    }

    private static UpdateOneTimePasscodeVerificationRequestDocument buildUpdateVerificationRequestDocument() {
        final UpdateOneTimePasscodeVerificationRequest request = UpdateOneTimePasscodeVerificationRequestMother.build();
        return new UpdateOneTimePasscodeVerificationRequestDocument(request);
    }

}
