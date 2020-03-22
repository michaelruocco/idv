package uk.co.idv.app.rest.verificationcontext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.api.verificationcontext.CreateContextRequestDocument;
import uk.co.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.idv.api.verificationcontext.VerificationContextDocument;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.domain.usecases.verificationcontext.FakeVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextControllerTest {

    private final VerificationContext context = VerificationContextMother.fake();

    private final FakeVerificationContextService service = new FakeVerificationContextService();

    private final VerificationContextController controller = new VerificationContextController(service);

    @BeforeEach
    void setUp() {
        service.setContextToReturn(context);
    }

    @Test
    void shouldPassCreateContextRequestToService() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        controller.createContext(requestDocument);

        final CreateContextRequest request = service.getLastCreateRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedContext() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final VerificationContextDocument responseDocument = response.getBody();
        assertThat(responseDocument.getAttributes()).isEqualTo(context);
    }

    @Test
    void shouldReturnCreatedStatus() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnLocationHeader() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/verification-contexts/%s", context.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldPassProvidedIdToService() {
        final UUID id = UUID.randomUUID();

        controller.getContext(id);

        final UUID expectedId = service.getLastLoadedId();
        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    void shouldReturnContext() {
        final UUID id = UUID.randomUUID();

        final VerificationContextDocument document = controller.getContext(id);

        assertThat(document.getAttributes()).isEqualTo(context);
    }

    @Test
    void shouldPassUpdateContextResultsRequestToService() {
        final UpdateContextResultsRequestDocument requestDocument = buildUpdateContextResultsRequestDocument();

        controller.updateContextResults(requestDocument);

        final RecordResultRequest request = service.getLastUpdateResultRequest();
        assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnContextFromUpdateResults() {
        final UpdateContextResultsRequestDocument requestDocument = buildUpdateContextResultsRequestDocument();

        final VerificationContextDocument document = controller.updateContextResults(requestDocument);

        assertThat(document.getAttributes()).isEqualTo(context);
    }

    private static CreateContextRequestDocument buildCreateContextRequestDocument() {
        final CreateContextRequest request = CreateContextRequest.builder().build();
        return new CreateContextRequestDocument(request);
    }

    private static UpdateContextResultsRequestDocument buildUpdateContextResultsRequestDocument() {
        final RecordResultRequest request = RecordResultRequest.builder().build();
        return new UpdateContextResultsRequestDocument(request);
    }

}
