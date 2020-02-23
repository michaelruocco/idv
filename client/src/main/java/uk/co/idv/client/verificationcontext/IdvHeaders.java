package uk.co.idv.client.verificationcontext;

import uk.co.mruoc.rest.client.header.Headers;

public interface IdvHeaders extends Headers {

    String getCorrelationId();

    String getChannelId();

}
