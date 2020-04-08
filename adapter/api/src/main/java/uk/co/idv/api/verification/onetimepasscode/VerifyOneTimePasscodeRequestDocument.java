package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class VerifyOneTimePasscodeRequestDocument extends ApiDocumentWithId<VerifyOneTimePasscodeRequest> {

    public VerifyOneTimePasscodeRequestDocument(final VerifyOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<VerifyOneTimePasscodeRequest> {

        private Data(final VerifyOneTimePasscodeRequest request) {
            super(request.getId(),"one-time-passcode-verifications", request);
        }

    }

}
