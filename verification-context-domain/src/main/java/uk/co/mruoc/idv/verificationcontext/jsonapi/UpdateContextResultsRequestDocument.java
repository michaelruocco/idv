package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.service.RecordResultRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class UpdateContextResultsRequestDocument extends ApiDocumentWithId<RecordResultRequest> {

    public UpdateContextResultsRequestDocument(final RecordResultRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<RecordResultRequest> {

        public Data(final RecordResultRequest attributes) {
            super(attributes.getContextId(), "verificationContexts", attributes);
        }

    }

}
