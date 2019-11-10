package uk.co.idv.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uk.co.idv.api.identity.IdentityNotFoundError;
import uk.co.idv.api.verificationcontext.error.ActivityNotSupportedError;
import uk.co.idv.api.verificationcontext.error.AliasNotSupportedError;
import uk.co.idv.api.verificationcontext.error.ChannelNotSupportedError;
import uk.co.idv.api.verificationcontext.error.InvalidJsonRequestError;
import uk.co.idv.api.verificationcontext.error.LockedOutError;
import uk.co.idv.api.verificationcontext.error.MethodAlreadyCompleteError;
import uk.co.idv.api.verificationcontext.error.NotNextMethodInSequenceError;
import uk.co.idv.api.verificationcontext.error.VerificationContextExpiredError;
import uk.co.idv.api.verificationcontext.error.VerificationContextNotFoundError;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.usecases.identity.IdentityService.IdentityNotFoundException;
import uk.co.idv.json.identity.AliasDeserializer.AliasNotSupportedException;
import uk.co.idv.domain.usecases.lockout.LockoutStateValidator.LockedOutException;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader.VerificationContextExpiredException;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader.VerificationContextNotFoundException;
import uk.co.mruoc.jsonapi.error.ApiError;
import uk.co.mruoc.jsonapi.error.ApiErrorDocument;
import uk.co.mruoc.jsonapi.error.ApiSingleErrorDocument;
import uk.co.mruoc.jsonapi.error.BadRequestError;
import uk.co.mruoc.jsonapi.error.InternalServerError;

@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorDocument> handleException(final Throwable e) {
        log.error("internal server error", e);
        return buildResponseEntity(new InternalServerError(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final HttpMessageNotReadableException e) {
        return buildResponseEntity(new InvalidJsonRequestError(e.getMessage()));
    }

    @ExceptionHandler(ChannelNotSupportedException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final ChannelNotSupportedException e) {
        return buildResponseEntity(new ChannelNotSupportedError(e.getMessage()));
    }

    @ExceptionHandler(ActivityNotSupportedException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final ActivityNotSupportedException e) {
        return buildResponseEntity(new ActivityNotSupportedError(e.getMessage()));
    }

    @ExceptionHandler(AliasNotSupportedException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final AliasNotSupportedException e) {
        return buildResponseEntity(new AliasNotSupportedError(e.getMessage()));
    }

    @ExceptionHandler(NotNextMethodInSequenceException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final NotNextMethodInSequenceException e) {
        return buildResponseEntity(new NotNextMethodInSequenceError(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final MethodArgumentTypeMismatchException e) {
        return buildResponseEntity(new BadRequestError(e.getMessage()));
    }

    @ExceptionHandler(VerificationContextNotFoundException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final VerificationContextNotFoundException e) {
        return buildResponseEntity(new VerificationContextNotFoundError(e.getMessage()));
    }

    @ExceptionHandler(LockedOutException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final LockedOutException e) {
        return buildResponseEntity(new LockedOutError(e.getLockoutState()));
    }

    @ExceptionHandler(VerificationContextExpiredException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final VerificationContextExpiredException e) {
        return buildResponseEntity(new VerificationContextExpiredError(e.getMessage()));
    }

    @ExceptionHandler(MethodAlreadyCompleteException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final MethodAlreadyCompleteException e) {
        return buildResponseEntity(new MethodAlreadyCompleteError(e.getMessage()));
    }

    @ExceptionHandler(IdentityNotFoundException.class)
    public ResponseEntity<ApiErrorDocument> handleException(final IdentityNotFoundException e) {
        return buildResponseEntity(new IdentityNotFoundError(e.getMessage()));
    }

    private ResponseEntity<ApiErrorDocument> buildResponseEntity(final ApiError error) {
        final ApiErrorDocument document = new ApiSingleErrorDocument(error);
        return new ResponseEntity<>(document, HttpStatus.valueOf(error.getStatus()));
    }

}
