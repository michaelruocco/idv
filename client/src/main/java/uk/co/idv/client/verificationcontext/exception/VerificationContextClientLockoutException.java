package uk.co.idv.client.verificationcontext.exception;

import uk.co.mruoc.rest.client.response.Response;

import java.util.UUID;

public class VerificationContextClientLockoutException extends VerificationContextClientException {

    public VerificationContextClientLockoutException(final UUID contextId, final Response response) {
        super(contextId, response);
    }

}
