package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class CreateOneTimePasscodeVerificationRequestDocument extends ApiDocument<CreateOneTimePasscodeVerificationRequest> {

    public CreateOneTimePasscodeVerificationRequestDocument(final CreateOneTimePasscodeVerificationRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<CreateOneTimePasscodeVerificationRequest> {

        private Data(final CreateOneTimePasscodeVerificationRequest request) {
            super("oneTimePasscodeVerifications", request);
        }

    }

}
