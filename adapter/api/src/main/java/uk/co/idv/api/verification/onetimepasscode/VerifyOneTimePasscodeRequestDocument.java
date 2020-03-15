package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.verification.onetimepasscode.VerifyOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocument;

public class VerifyOneTimePasscodeRequestDocument extends ApiDocument<VerifyOneTimePasscodeRequest> {

    public VerifyOneTimePasscodeRequestDocument(final VerifyOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<VerifyOneTimePasscodeRequest> {

        private Data(final VerifyOneTimePasscodeRequest request) {
            super(request.getId(),"oneTimePasscodeVerifications", request);
        }

    }

}
