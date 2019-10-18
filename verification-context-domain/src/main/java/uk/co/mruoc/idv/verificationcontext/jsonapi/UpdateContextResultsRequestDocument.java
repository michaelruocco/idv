package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.service.RecordResultRequest;
import uk.co.mruoc.jsonapi.JsonApiDocumentWithId;

public class UpdateContextResultsRequestDocument extends JsonApiDocumentWithId<RecordResultRequest> {

    public UpdateContextResultsRequestDocument(final RecordResultRequest attributes) {
        super(attributes.getContextId(),"verificationContexts", attributes);
    }

}
