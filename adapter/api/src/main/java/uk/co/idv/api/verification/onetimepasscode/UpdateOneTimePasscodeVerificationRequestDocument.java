package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocument;

public class UpdateOneTimePasscodeVerificationRequestDocument extends ApiDocument<UpdateOneTimePasscodeVerificationRequest> {

    public UpdateOneTimePasscodeVerificationRequestDocument(final UpdateOneTimePasscodeVerificationRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<UpdateOneTimePasscodeVerificationRequest> {

        private Data(final UpdateOneTimePasscodeVerificationRequest request) {
            super(request.getId(),"oneTimePasscodeVerifications", request);
        }

    }

}
