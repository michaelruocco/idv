package uk.co.idv.app.rest.verificationcontext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.idv.api.verificationcontext.CreateContextRequestDocument;
import uk.co.mruoc.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.mruoc.idv.api.verificationcontext.VerificationContextDocument;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.domain.usecases.verificationcontext.FakeVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.LoadContextRequest;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextControllerTest {

    private final VerificationContext context = new FakeVerificationContext();

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
        Assertions.assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedContext() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final VerificationContextDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
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
        final String expectedUri = String.format("/verificationContexts/%s", context.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldPassLoadContextRequestToServiceWithProvidedId() {
        final UUID id = UUID.randomUUID();

        controller.getContext(id);

        final LoadContextRequest request = service.getLastLoadRequest();
        assertThat(request.getId()).isEqualTo(id);
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
        Assertions.assertThat(request).isEqualTo(requestDocument.getAttributes());
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
