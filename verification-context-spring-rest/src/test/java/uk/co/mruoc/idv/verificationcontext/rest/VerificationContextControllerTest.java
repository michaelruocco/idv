package uk.co.mruoc.idv.verificationcontext.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.LoadContextRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.RecordResultRequest;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;
import uk.co.mruoc.idv.verificationcontext.jsonapi.CreateContextRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.UpdateContextResultsRequestDocument;
import uk.co.mruoc.idv.verificationcontext.jsonapi.VerificationContextDocument;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerificationContextControllerTest {

    private static final VerificationContext CONTEXT = VerificationContext.builder()
            .id(UUID.randomUUID())
            .build();

    private final VerificationContextService service = mock(VerificationContextService.class);

    private final VerificationContextController controller = new VerificationContextController(service);

    @Test
    void shouldPassCreateContextRequestToService() {
        given(service.create(any(CreateContextRequest.class))).willReturn(CONTEXT);
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        controller.createContext(requestDocument);

        final ArgumentCaptor<CreateContextRequest> captor = ArgumentCaptor.forClass(CreateContextRequest.class);
        verify(service).create(captor.capture());
        final CreateContextRequest request = captor.getValue();
        Assertions.assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnCreatedContext() {
        given(service.create(any(CreateContextRequest.class))).willReturn(CONTEXT);
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final VerificationContextDocument responseDocument = response.getBody();
        assertThat(responseDocument).isNotNull();
        assertThat(responseDocument.getAttributes()).isEqualTo(CONTEXT);
    }

    @Test
    void shouldReturnCreatedStatus() {
        given(service.create(any(CreateContextRequest.class))).willReturn(CONTEXT);
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnLocationHeader() {
        given(service.create(any(CreateContextRequest.class))).willReturn(CONTEXT);
        final CreateContextRequestDocument requestDocument = buildCreateContextRequestDocument();

        final ResponseEntity<VerificationContextDocument> response = controller.createContext(requestDocument);

        final HttpHeaders headers = response.getHeaders();
        final String expectedUri = String.format("/verificationContexts/%s", CONTEXT.getId());
        assertThat(headers.get("Location")).containsExactly(expectedUri);
    }

    @Test
    void shouldPassLoadContextRequestToServiceWithProvidedId() {
        given(service.load(any(LoadContextRequest.class))).willReturn(CONTEXT);
        final UUID id = UUID.randomUUID();

        controller.getContext(id);

        final ArgumentCaptor<LoadContextRequest> captor = ArgumentCaptor.forClass(LoadContextRequest.class);
        verify(service).load(captor.capture());
        final LoadContextRequest request = captor.getValue();
        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContext() {
        given(service.load(any(LoadContextRequest.class))).willReturn(CONTEXT);
        final UUID id = UUID.randomUUID();

        final VerificationContextDocument document = controller.getContext(id);

        assertThat(document.getAttributes()).isEqualTo(CONTEXT);
    }

    @Test
    void shouldPassUpdateContextResultsRequestToService() {
        given(service.recordResult(any(RecordResultRequest.class))).willReturn(CONTEXT);
        final UpdateContextResultsRequestDocument requestDocument = buildUpdateContextResultsRequestDocument();

        controller.updateContextResults(requestDocument);

        final ArgumentCaptor<RecordResultRequest> captor = ArgumentCaptor.forClass(RecordResultRequest.class);
        verify(service).recordResult(captor.capture());
        final RecordResultRequest request = captor.getValue();
        Assertions.assertThat(request).isEqualTo(requestDocument.getAttributes());
    }

    @Test
    void shouldReturnContextFromUpdateResults() {
        given(service.recordResult(any(RecordResultRequest.class))).willReturn(CONTEXT);
        final UpdateContextResultsRequestDocument requestDocument = buildUpdateContextResultsRequestDocument();

        final VerificationContextDocument document = controller.updateContextResults(requestDocument);

        assertThat(document.getAttributes()).isEqualTo(CONTEXT);
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
