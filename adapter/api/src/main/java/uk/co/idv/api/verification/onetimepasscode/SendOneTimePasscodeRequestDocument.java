package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class SendOneTimePasscodeRequestDocument extends ApiDocument<SendOneTimePasscodeRequest> {

    public SendOneTimePasscodeRequestDocument(final SendOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<SendOneTimePasscodeRequest> {

        private Data(final SendOneTimePasscodeRequest request) {
            super("oneTimePasscodeVerifications", request);
        }

    }

}
