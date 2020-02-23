package uk.co.idv.client.verificationcontext.exception;

import uk.co.mruoc.rest.client.response.Response;

import java.util.Map;
import java.util.UUID;

public class VerificationContextClientException extends RuntimeException {

    private final UUID contextId;
    private final Response response;

    public VerificationContextClientException(final UUID contextId, final Response response) {
        super(toMessage(response));
        this.contextId = contextId;
        this.response = response;
    }

    public UUID getContextId() {
        return contextId;
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getBody() {
        return response.getBody();
    }

    public Map<String, String> getHeaders() {
        return response.getHeadersAsMap();
    }

    private static String toMessage(final Response response) {
        return String.format("response status code %d, body %s", response.getStatusCode(), response.getBody());
    }

}
