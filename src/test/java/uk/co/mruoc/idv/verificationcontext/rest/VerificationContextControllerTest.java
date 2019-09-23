package uk.co.mruoc.idv.verificationcontext.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.FakeVerificationContextService;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest;
import uk.co.mruoc.idv.verificationcontext.jsonapi.CreateContextRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.VerificationContextDocument;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextControllerTest {

    private static final VerificationContext CONTEXT = VerificationContext.builder()
            .id(UUID.randomUUID())
            .build();

    private final FakeVerificationContextService service = new FakeVerificationContextService();

    private final VerificationContextController controller = new VerificationContextController(service);

    @BeforeEach
    void setUp() {
        service.setContextToReturn(CONTEXT);
    }

    @Test
    void shouldPassCreateContextRequestToService() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        controller.createContext(requestDocument);

        assertThat(service.getLastCreateRequest()).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedContext() {
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final VerificationContextDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
        assertThat(responseDocument.getAttributes()).isEqualTo(CONTEXT);
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
        final String expectedUri = String.format("/verificationContexts/%s", CONTEXT.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldPassGetContextRequestToServiceWithProvidedId() {
        final UUID id = UUID.randomUUID();

        controller.getContext(id);

        final GetContextRequest request = service.getLastGetRequest();
        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContext() {
        final UUID id = UUID.randomUUID();

        final VerificationContextDocument document = controller.getContext(id);

        assertThat(document.getAttributes()).isEqualTo(CONTEXT);
    }

    private CreateContextRequestDocument buildCreateContextRequestDocument() {
        final CreateContextRequest request = CreateContextRequest.builder().build();
        return new CreateContextRequestDocument(request);
    }

}
