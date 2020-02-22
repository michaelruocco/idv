package uk.co.idv.client.verificationcontext.exception;

import uk.co.mruoc.rest.client.response.Response;

import java.util.UUID;

public class VerificationContextClientExpiredException extends VerificationContextClientException {

    public VerificationContextClientExpiredException(final UUID contextId, final Response response) {
        super(contextId, response);
    }

}
