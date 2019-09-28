package uk.co.mruoc.idv.verificationcontext;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uk.co.mruoc.idv.api.activity.ActivityDeserializer.ActivityNotSupportedException;
import uk.co.mruoc.idv.api.channel.ChannelDeserializer.ChannelNotSupportedException;
import uk.co.mruoc.idv.identity.api.AliasDeserializer.AliasNotSupportedException;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextNotFoundException;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.ActivityNotSupportedErrorItem;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.AliasNotSupportedErrorItem;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.ChannelNotSupportedErrorItem;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.InvalidJsonRequestErrorItem;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.NotNextMethodInSequenceErrorItem;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.VerificationContextNotFoundErrorItem;
import uk.co.mruoc.jsonapi.error.BadRequestErrorItem;
import uk.co.mruoc.jsonapi.error.InternalServerErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiSingleErrorDocument;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ApplicationErrorHandlerTest {

    private static final String MESSAGE = "error message";

    private final RecursiveComparisonConfiguration comparisonConfiguration = new JsonApiErrorDocumentComparisonConfiguration();
    private final ApplicationErrorHandler handler = new ApplicationErrorHandler();

    @Test
    void shouldReturnDocumentForUnexpectedException() {
        final Throwable exception = new Exception(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new InternalServerErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForHttpMessageNotReadableException() {
        final HttpMessageNotReadableException exception = new HttpMessageNotReadableException(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new InvalidJsonRequestErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForChannelNotSupportedException() {
        final ChannelNotSupportedException exception = new ChannelNotSupportedException(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new ChannelNotSupportedErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForActivityNotSupportedException() {
        final ActivityNotSupportedException exception = new ActivityNotSupportedException(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new ActivityNotSupportedErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForAliasNotSupportedException() {
        final AliasNotSupportedException exception = new AliasNotSupportedException(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new AliasNotSupportedErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForNotNextMethodInSequenceException() {
        final NotNextMethodInSequenceException exception = new NotNextMethodInSequenceException(MESSAGE);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new NotNextMethodInSequenceErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForMethodArgumentTypeMismatchException() {
        final MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new BadRequestErrorItem(exception.getMessage())));
    }

    @Test
    void shouldReturnDocumentForVerificationContextNotFoundException() {
        final VerificationContextNotFoundException exception = mock(VerificationContextNotFoundException.class);

        final ResponseEntity<JsonApiErrorDocument> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .usingRecursiveComparison(comparisonConfiguration)
                .isEqualTo(toDocument(new VerificationContextNotFoundErrorItem(exception.getMessage())));
    }

    private static JsonApiErrorDocument toDocument(final JsonApiErrorItem item) {
        return new JsonApiSingleErrorDocument(item);
    }

    private static class JsonApiErrorDocumentComparisonConfiguration extends RecursiveComparisonConfiguration {

        private JsonApiErrorDocumentComparisonConfiguration() {
            ignoreFields("error.id");
        }

    }

}
