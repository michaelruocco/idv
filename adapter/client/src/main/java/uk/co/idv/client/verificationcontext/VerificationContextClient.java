package uk.co.idv.client.verificationcontext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.api.ApiObjectMapperSingleton;
import uk.co.idv.api.verificationcontext.VerificationContextDocument;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientExpiredException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientLockoutException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientNotFoundException;
import uk.co.idv.client.verificationcontext.exception.VerificationContextClientException;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.utils.json.converter.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.mruoc.rest.client.RestClient;
import uk.co.mruoc.rest.client.SimpleRestClient;
import uk.co.mruoc.rest.client.header.Headers;
import uk.co.mruoc.rest.client.response.Response;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class VerificationContextClient {

    private final String baseUrl;
    private final RestClient client;
    private final JsonConverter converter;

    public VerificationContextClient(final String baseUrl) {
        this(baseUrl, new SimpleRestClient(), new JacksonJsonConverter(ApiObjectMapperSingleton.instance()));
    }

    public VerificationContext getContext(final UUID id, final Headers headers) {
        final Response response = client.get(buildUri(id), headers);
        if (response.is2xx()) {
            return handleSuccess(response);
        }
        if (response.getStatusCode() == 404) {
            throw new VerificationContextClientNotFoundException(id, response);
        }
        if (response.getStatusCode() == 410) {
            throw new VerificationContextClientExpiredException(id, response);
        }
        if (response.getStatusCode() == 423) {
            throw new VerificationContextClientLockoutException(id, response);
        }
        throw new VerificationContextClientException(id, response);
    }

    private VerificationContext handleSuccess(final Response response) {
        final VerificationContextDocument document = toDocument(response.getBody());
        return document.getAttributes();
    }

    private String buildUri(final UUID id) {
        return String.format("%s/verificationContexts/%s", baseUrl, id);
    }

    private VerificationContextDocument toDocument(final String body) {
        return converter.toObject(body, VerificationContextDocument.class);
    }

}
