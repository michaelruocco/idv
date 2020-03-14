package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.verification.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocument;

public class ResendOneTimePasscodeRequestDocument extends ApiDocument<ResendOneTimePasscodeRequest> {

    public ResendOneTimePasscodeRequestDocument(final ResendOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<ResendOneTimePasscodeRequest> {

        private Data(final ResendOneTimePasscodeRequest request) {
            super(request.getId(),"oneTimePasscodeVerifications", request);
        }

    }

}
