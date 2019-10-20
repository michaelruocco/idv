package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import uk.co.mruoc.idv.mongo.dao.activity.FakeActivityDocument;
import uk.co.mruoc.idv.mongo.dao.channel.FakeChannelDocument;
import uk.co.mruoc.idv.mongo.identity.dao.FakeCreditCardNumberAliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.FakeIdentityDocument;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;

public class FakeVerificationContextDocument extends VerificationContextDocument {

    private final FakeVerificationContext context = new FakeVerificationContext();

    public FakeVerificationContextDocument() {
        setId("eaca769b-c8ac-42fc-ba6a-97e6f1be36f8");
        setChannel(new FakeChannelDocument());
        setProvidedAlias(new FakeCreditCardNumberAliasDocument());
        setIdentity(new FakeIdentityDocument("d23d923a-521a-422d-9ba9-8cb1c756dbee"));
        setActivity(new FakeActivityDocument("2019-09-21T20:40:29.061224Z"));
        setCreated("2019-09-21T20:48:32.233721Z");
        setExpiry("2019-09-21T20:48:32.233721Z");

    }
}
