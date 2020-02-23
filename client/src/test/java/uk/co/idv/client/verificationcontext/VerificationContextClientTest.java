package uk.co.idv.client.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.api.verificationcontext.UpdateContextResultsRequestDocument;
import uk.co.idv.api.verificationcontext.VerificationContextDocument;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientExpiredException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientLockoutException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientNotFoundException;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.usecases.verificationcontext.RecordResultRequest;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.mruoc.rest.client.header.Headers;
import uk.co.mruoc.rest.client.response.Response;
import uk.co.mruoc.rest.client.test.DefaultFakeRestClient;
import uk.co.mruoc.rest.client.test.FakeRestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextClientTest {

    private static final String BASE_URL = "http://localhost:8081";
    private static final UUID ID = UUID.randomUUID();
    private static final String BODY = "body";

    private final FakeRestClient restClient = new DefaultFakeRestClient();
    private final JsonConverter converter = mock(JsonConverter.class);

    private final VerificationContextClient client = new VerificationContextClient(BASE_URL, restClient, converter);


    private final Headers headers = mock(Headers.class);

    @Test
    void shouldIncludeIdInUriWhenGettingContext() {
        givenResponseWith(200);
        givenResponseBodyConvertedToVerificationContextDocument();

        client.getContext(ID, headers);

        final String expectedUri = String.format("%s/verificationContexts/%s", BASE_URL, ID.toString());
        assertThat(restClient.lastRequestUri()).isEqualTo(expectedUri);
    }

    @Test
    void shouldPassHeadersWhenGettingContext() {
        givenResponseWith(200);
        givenResponseBodyConvertedToVerificationContextDocument();

        client.getContext(ID, headers);

        assertThat(restClient.lastRequestHeaders().hasSameValues(headers)).isTrue();
    }

    @Test
    void shouldReturnVerificationContextIfResponseIsSuccessfulWhenGettingContext() {
        givenResponseWith(200);
        final VerificationContextDocument document = givenResponseBodyConvertedToVerificationContextDocument();

        final VerificationContext context = client.getContext(ID, headers);

        assertThat(context).isEqualTo(document.getAttributes());
    }

    @Test
    void shouldThrowExceptionIfResponseIsUnsuccessfulWhenGettingContext() {
        final int statusCode = 400;
        givenResponseWith(statusCode);

        final VerificationContextClientException error = (VerificationContextClientException) catchThrowable(() -> client.getContext(ID, headers));

        assertThat(error.getContextId()).isEqualTo(ID);
        assertThat(error.getStatusCode()).isEqualTo(statusCode);
        assertThat(error.getBody()).isEqualTo(BODY);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextIsNotFoundWhenGettingContext() {
        givenResponseWith(404);

        final Throwable error = catchThrowable(() -> client.getContext(ID, headers));

        assertThat(error).isInstanceOf(VerificationContextClientNotFoundException.class);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextHasExpiredWhenGettingContext() {
        givenResponseWith(410);

        final Throwable error = catchThrowable(() -> client.getContext(ID, headers));

        assertThat(error).isInstanceOf(VerificationContextClientExpiredException.class);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextIsLockedOutWhenGettingContext() {
        givenResponseWith(423);

        final Throwable error = catchThrowable(() -> client.getContext(ID, headers));

        assertThat(error).isInstanceOf(VerificationContextClientLockoutException.class);
    }

    @Test
    void shouldIncludeIdInUriWhenUpdatingContext() {
        givenResponseWith(200);
        givenResponseBodyConvertedToVerificationContextDocument();
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        client.updateContext(request, headers);

        final String expectedUri = String.format("%s/verificationContexts/%s", BASE_URL, ID.toString());
        assertThat(restClient.lastRequestUri()).isEqualTo(expectedUri);
    }

    @Test
    void shouldPassHeadersWhenUpdatingContext() {
        givenResponseWith(200);
        givenResponseBodyConvertedToVerificationContextDocument();
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        client.updateContext(request, headers);

        assertThat(restClient.lastRequestHeaders().hasSameValues(headers)).isTrue();
    }

    @Test
    void shouldReturnVerificationContextIfResponseIsSuccessfulWhenUpdatingContext() {
        givenResponseWith(200);
        final VerificationContextDocument document = givenResponseBodyConvertedToVerificationContextDocument();
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        final VerificationContext context = client.updateContext(request, headers);

        assertThat(context).isEqualTo(document.getAttributes());
    }

    @Test
    void shouldThrowExceptionIfResponseIsUnsuccessfulWhenUpdatingContext() {
        final int statusCode = 400;
        givenResponseWith(statusCode);
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        final VerificationContextClientException error = (VerificationContextClientException) catchThrowable(() -> client.updateContext(request, headers));

        assertThat(error.getContextId()).isEqualTo(ID);
        assertThat(error.getStatusCode()).isEqualTo(statusCode);
        assertThat(error.getBody()).isEqualTo(BODY);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextIsNotFoundWhenUpdatingContext() {
        givenResponseWith(404);
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        final Throwable error = catchThrowable(() -> client.updateContext(request, headers));

        assertThat(error).isInstanceOf(VerificationContextClientNotFoundException.class);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextHasExpiredWhenUpdatingContext() {
        givenResponseWith(410);
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        final Throwable error = catchThrowable(() -> client.updateContext(request, headers));

        assertThat(error).isInstanceOf(VerificationContextClientExpiredException.class);
    }

    @Test
    void shouldThrowNotFoundExceptionIfContextIsLockedOutWhenUpdatingContext() {
        givenResponseWith(423);
        final RecordResultRequest request = buildRecordResultRequest();
        givenRecordResultRequestIsConvertedToJsonBody(request);

        final Throwable error = catchThrowable(() -> client.updateContext(request, headers));

        assertThat(error).isInstanceOf(VerificationContextClientLockoutException.class);
    }

    private void givenResponseWith(final int statusCode) {
        final Response response = new Response.ResponseBuilder()
                .setStatusCode(statusCode)
                .setBody(BODY)
                .build();
        restClient.cannedResponse(response);
    }

    private VerificationContextDocument givenResponseBodyConvertedToVerificationContextDocument() {
        final VerificationContextDocument document = new VerificationContextDocument(new FakeVerificationContext());
        given(converter.toObject(BODY, VerificationContextDocument.class)).willReturn(document);
        return document;
    }

    private RecordResultRequest buildRecordResultRequest() {
        return RecordResultRequest.builder()
                .contextId(ID)
                .result(new FakeVerificationResultSuccessful(PushNotification.NAME))
                .build();
    }

    private void givenRecordResultRequestIsConvertedToJsonBody(final RecordResultRequest request) {
        final UpdateContextResultsRequestDocument document = new UpdateContextResultsRequestDocument(request);
        given(converter.toJson(document)).willReturn(BODY);
    }

}
