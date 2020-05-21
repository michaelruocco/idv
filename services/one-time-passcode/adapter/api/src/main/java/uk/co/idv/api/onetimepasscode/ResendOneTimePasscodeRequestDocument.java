package uk.co.idv.api.onetimepasscode;

import uk.co.idv.domain.usecases.onetimepasscode.send.ResendOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocument;

public class ResendOneTimePasscodeRequestDocument extends ApiDocument<ResendOneTimePasscodeRequest> {

    public ResendOneTimePasscodeRequestDocument(final ResendOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<ResendOneTimePasscodeRequest> {

        private Data(final ResendOneTimePasscodeRequest request) {
            super(request.getId(),"one-time-passcode-verifications", request);
        }

    }

}
