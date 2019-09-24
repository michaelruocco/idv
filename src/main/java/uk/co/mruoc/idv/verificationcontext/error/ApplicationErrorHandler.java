package uk.co.mruoc.idv.verificationcontext.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.mruoc.idv.api.activity.ActivityDeserializer.ActivityNotSupportedException;
import uk.co.mruoc.idv.api.channel.ChannelDeserializer.ChannelNotSupportedException;
import uk.co.mruoc.idv.identity.api.AliasDeserializer.AliasNotSupportedException;
import uk.co.mruoc.jsonapi.error.InternalServerErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiErrorDocument;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;
import uk.co.mruoc.jsonapi.error.JsonApiSingleErrorDocument;

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonApiErrorDocument> handleException(final Exception e) {
        log.error("internal server error", e);
        return buildResponseEntity(new InternalServerErrorItem(e.getMessage()));
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

    private ResponseEntity<JsonApiErrorDocument> buildResponseEntity(final JsonApiErrorItem error) {
        final JsonApiErrorDocument document = new JsonApiSingleErrorDocument(error);
        return new ResponseEntity<>(document, HttpStatus.valueOf(error.getStatus()));
    }

}
