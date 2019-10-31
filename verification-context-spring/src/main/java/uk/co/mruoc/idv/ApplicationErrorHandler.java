package uk.co.mruoc.idv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uk.co.mruoc.idv.domain.exception.ActivityNotSupportedException;
import uk.co.mruoc.idv.domain.exception.ChannelNotSupportedException;
import uk.co.mruoc.idv.identity.api.AliasDeserializer.AliasNotSupportedException;
import uk.co.mruoc.idv.identity.domain.service.IdentityService.IdentityNotFoundException;
import uk.co.mruoc.idv.identity.jsonapi.error.IdentityNotFoundError;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator.LockedOutException;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextExpiredException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextNotFoundException;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.ActivityNotSupportedError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.AliasNotSupportedError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.ChannelNotSupportedError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.InvalidJsonRequestError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.LockedOutError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.MethodAlreadyCompleteError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.NotNextMethodInSequenceError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.VerificationContextExpiredError;
import uk.co.mruoc.idv.verificationcontext.jsonapi.error.VerificationContextNotFoundError;
import uk.co.mruoc.jsonapi.error.BadRequestError;
import uk.co.mruoc.jsonapi.error.InternalServerError;
import uk.co.mruoc.jsonapi.error.ApiErrorDocument;
import uk.co.mruoc.jsonapi.error.ApiError;
import uk.co.mruoc.jsonapi.error.ApiSingleErrorDocument;

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
