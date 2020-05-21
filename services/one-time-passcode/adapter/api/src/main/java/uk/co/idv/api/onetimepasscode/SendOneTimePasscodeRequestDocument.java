package uk.co.idv.api.onetimepasscode;

import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequest;
import uk.co.mruoc.jsonapi.ApiData;
import uk.co.mruoc.jsonapi.ApiDocument;

public class SendOneTimePasscodeRequestDocument extends ApiDocument<SendOneTimePasscodeRequest> {

    public SendOneTimePasscodeRequestDocument(final SendOneTimePasscodeRequest attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiData<SendOneTimePasscodeRequest> {

        private Data(final SendOneTimePasscodeRequest request) {
            super("one-time-passcode-verifications", request);
        }

    }

}
