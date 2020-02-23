package uk.co.idv.client.verificationcontext.exception;

import uk.co.mruoc.rest.client.response.Response;

import java.util.UUID;

public class VerificationContextClientNotFoundException extends VerificationContextClientException {

    public VerificationContextClientNotFoundException(final UUID contextId, final Response response) {
        super(contextId, response);
    }

}
