package uk.co.idv.repository.mongo.verificationcontext;

import uk.co.idv.repository.mongo.activity.ActivityDocumentMother;
import uk.co.idv.repository.mongo.channel.ChannelDocumentMother;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentMother;
import uk.co.idv.repository.mongo.identity.IdentityDocumentMother;

public class VerificationContextDocumentMother {

    public static VerificationContextDocument fake() {
        final VerificationContextDocument document = new VerificationContextDocument();
        document.setId("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8");
        document.setChannel(ChannelDocumentMother.fake());
        document.setProvidedAlias(AliasDocumentMother.creditCard());
        document.setIdentity(IdentityDocumentMother.build("d23d923a-521a-422d-9ba9-8cb1c756dbee"));
        document.setActivity(ActivityDocumentMother.withTimestamp("2019-09-21T20:40:29.061224Z"));
        document.setCreated("2019-09-21T20:48:32.233721Z");
        document.setExpiry("2019-09-21T20:48:32.233721Z");
        return document;
    }

}
