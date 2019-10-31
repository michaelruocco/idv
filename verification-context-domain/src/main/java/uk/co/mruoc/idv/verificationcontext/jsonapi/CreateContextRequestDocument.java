package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.service.CreateContextRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class CreateContextRequestDocument extends ApiDocument<CreateContextRequest> {

    public CreateContextRequestDocument(final CreateContextRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<CreateContextRequest> {

        private Data(final CreateContextRequest request) {
            super("verificationContexts", request);
        }

    }

}
