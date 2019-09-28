package uk.co.mruoc.idv.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final Throwable e) {
        log.error("internal server error", e);
        return buildResponseEntity(new InternalServerErrorItem(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final HttpMessageNotReadableException e) {
        return buildResponseEntity(new InvalidJsonRequestErrorItem(e.getMessage()));
    }

    @ExceptionHandler(ChannelNotSupportedException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final ChannelNotSupportedException e) {
        return buildResponseEntity(new ChannelNotSupportedErrorItem(e.getMessage()));
    }

    @ExceptionHandler(ActivityNotSupportedException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final ActivityNotSupportedException e) {
        return buildResponseEntity(new ActivityNotSupportedErrorItem(e.getMessage()));
    }

    @ExceptionHandler(AliasNotSupportedException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final AliasNotSupportedException e) {
        return buildResponseEntity(new AliasNotSupportedErrorItem(e.getMessage()));
    }

    @ExceptionHandler(NotNextMethodInSequenceException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final NotNextMethodInSequenceException e) {
        return buildResponseEntity(new NotNextMethodInSequenceErrorItem(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final MethodArgumentTypeMismatchException e) {
        return buildResponseEntity(new BadRequestErrorItem(e.getMessage()));
    }

    @ExceptionHandler(VerificationContextNotFoundException.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final VerificationContextNotFoundException e) {
        return buildResponseEntity(new VerificationContextNotFoundErrorItem(e.getMessage()));
    }

    private ResponseEntity<JsonApiErrorDocument> buildResponseEntity(final JsonApiErrorItem error) {
        final JsonApiErrorDocument document = new JsonApiSingleErrorDocument(error);
        return new ResponseEntity<>(document, HttpStatus.valueOf(error.getStatus()));
    }

}
