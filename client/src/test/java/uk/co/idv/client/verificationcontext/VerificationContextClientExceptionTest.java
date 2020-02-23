package uk.co.idv.client.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientException;
import uk.co.mruoc.rest.client.response.Response;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextClientExceptionTest {

    private static final UUID CONTEXT_ID = UUID.randomUUID();

    private final Response response = mock(Response.class);

    @Test
    void shouldReturnContextId() {
        final VerificationContextClientException error = new VerificationContextClientException(CONTEXT_ID, response);

        assertThat(error.getContextId()).isEqualTo(CONTEXT_ID);
    }

    @Test
    void shouldReturnMessage() {
        final int statusCode = 404;
        final String body = "{\"value\":\"body\"}";
        given(response.getStatusCode()).willReturn(statusCode);
        given(response.getBody()).willReturn(body);

        final Throwable error = new VerificationContextClientException(CONTEXT_ID, response);

        final String expectedMessage = String.format("response status code %d, body %s", statusCode, body);
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnResponseStatusCode() {
        final int statusCode = 404;
        given(response.getStatusCode()).willReturn(statusCode);

        final VerificationContextClientException error = new VerificationContextClientException(CONTEXT_ID, response);

        assertThat(error.getStatusCode()).isEqualTo(statusCode);
    }

    @Test
    void shouldReturnResponseBody() {
        final String body = "body";
        given(response.getBody()).willReturn(body);

        final VerificationContextClientException error = new VerificationContextClientException(CONTEXT_ID, response);

        assertThat(error.getBody()).isEqualTo(body);
    }

    @Test
    void shouldReturnResponseHeaders() {
        final Map<String, String> headers = Collections.emptyMap();
        given(response.getHeadersAsMap()).willReturn(headers);

        final VerificationContextClientException error = new VerificationContextClientException(CONTEXT_ID, response);

        assertThat(error.getHeaders()).isEqualTo(headers);
    }

}
