package uk.co.mruoc.idv.mongo.verificationcontext.dao;

public class FakeIneligibleDocument extends EligibilityDocument {

    public FakeIneligibleDocument() {
        setEligible(false);
        setReason("fake-reason");
    }

}
