package uk.co.idv.api.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.mruoc.jsonapi.ApiDataWithId;
import uk.co.mruoc.jsonapi.ApiDocumentWithId;

public class OneTimePasscodeVerificationDocument extends ApiDocumentWithId<OneTimePasscodeVerification> {

    public OneTimePasscodeVerificationDocument(final OneTimePasscodeVerification attributes) {
        super(new Data(attributes));
    }

    private static class Data extends ApiDataWithId<OneTimePasscodeVerification> {

        private Data(final OneTimePasscodeVerification attributes) {
            super(attributes.getId(), "one-time-passcode-verifications", attributes);
        }

    }

}
