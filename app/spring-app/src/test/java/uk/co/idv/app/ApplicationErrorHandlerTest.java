package uk.co.mruoc.idv;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uk.co.mruoc.idv.api.identity.IdentityNotFoundError;
import uk.co.mruoc.idv.api.verificationcontext.error.ActivityNotSupportedError;
import uk.co.mruoc.idv.api.verificationcontext.error.AliasNotSupportedError;
import uk.co.mruoc.idv.api.verificationcontext.error.ChannelNotSupportedError;
import uk.co.mruoc.idv.api.verificationcontext.error.InvalidJsonRequestError;
import uk.co.mruoc.idv.api.verificationcontext.error.LockedOutError;
import uk.co.mruoc.idv.api.verificationcontext.error.MethodAlreadyCompleteError;
import uk.co.mruoc.idv.api.verificationcontext.error.NotNextMethodInSequenceError;
import uk.co.mruoc.idv.api.verificationcontext.error.VerificationContextExpiredError;
import uk.co.mruoc.idv.api.verificationcontext.error.VerificationContextNotFoundError;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.idv.domain.usecases.identity.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.idv.json.identity.AliasDeserializer.AliasNotSupportedException;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator.LockedOutException;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextExpiredException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextNotFoundException;
import uk.co.mruoc.jsonapi.error.ApiError;
import uk.co.mruoc.jsonapi.error.ApiErrorDocument;
import uk.co.mruoc.jsonapi.error.ApiSingleErrorDocument;
import uk.co.mruoc.jsonapi.error.BadRequestError;
import uk.co.mruoc.jsonapi.error.InternalServerError;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ApplicationErrorHandlerTest {

    private static final String MESSAGE = "error message";

    private final RecursiveComparisonConfiguration comparisonConfiguration = new JsonApiErrorDocumentComparisonConfiguration();
    private final ApplicationErrorHandler handler = new ApplicationErrorHandler();

    @Test
    void shouldReturnDocumentForUnexpectedException() {
        final Throwable exception = new Exception(MESSAGE);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new InternalServerError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForHttpMessageNotReadableException() {
        final HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new InvalidJsonRequestError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForChannelNotSupportedException() {
        final ChannelNotSupportedException exception = new ChannelNotSupportedException(MESSAGE);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new ChannelNotSupportedError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForActivityNotSupportedException() {
        final ActivityNotSupportedException exception = new ActivityNotSupportedException(MESSAGE);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new ActivityNotSupportedError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForAliasNotSupportedException() {
        final AliasNotSupportedException exception = new AliasNotSupportedException(MESSAGE);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new AliasNotSupportedError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForNotNextMethodInSequenceException() {
        final NotNextMethodInSequenceException exception = new NotNextMethodInSequenceException(MESSAGE);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new NotNextMethodInSequenceError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForMethodArgumentTypeMismatchException() {
        final MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new BadRequestError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForVerificationContextNotFoundException() {
        final UUID contextId = UUID.randomUUID();
        final VerificationContextNotFoundException exception = new VerificationContextNotFoundException(contextId);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new VerificationContextNotFoundError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForLockedOutException() {
        final LockedOutException exception = new LockedOutException(mock(LockoutState.class));

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.LOCKED);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new LockedOutError(exception.getLockoutState())));
    }

    @Test
    void shouldReturnDocumentForVerificationContextExpired() {
        final VerificationContext context = mock(VerificationContext.class);
        final VerificationContextExpiredException exception = new VerificationContextExpiredException(context, Instant.now());

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.GONE);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new VerificationContextExpiredError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForMethodAlreadyCompleted() {
        final String methodName = "method-name";
        final MethodAlreadyCompleteException exception = new MethodAlreadyCompleteException(methodName);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new MethodAlreadyCompleteError(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForIdentityNotFoundCompleted() {
        final Alias alias = AliasesMother.creditCardNumber();
        final IdentityNotFoundException exception = new IdentityNotFoundException(alias);

        final ResponseEntity<ApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new IdentityNotFoundError(exception.getMessage())));
    }

    private static ApiErrorDocument toDocument(final ApiError item) {
        return new ApiSingleErrorDocument(item);
    }

    private static class JsonApiErrorDocumentComparisonConfiguration extends RecursiveComparisonConfiguration {

        private JsonApiErrorDocumentComparisonConfiguration() {
            ignoreFields("error.id");
        }

    }

}
