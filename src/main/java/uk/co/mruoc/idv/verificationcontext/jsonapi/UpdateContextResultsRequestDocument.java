package uk.co.mruoc.idv.verificationcontext.jsonapi;

import uk.co.mruoc.idv.verificationcontext.domain.service.UpdateContextResultRequest;
import uk.co.mruoc.jsonapi.JsonApiDocumentWithId;

public class UpdateContextResultsRequestDocument extends JsonApiDocumentWithId<UpdateContextResultRequest> {

    public UpdateContextResultsRequestDocument(final UpdateContextResultRequest attributes) {
        super(attributes.getContextId(),"verificationContexts", attributes);
    }

}
