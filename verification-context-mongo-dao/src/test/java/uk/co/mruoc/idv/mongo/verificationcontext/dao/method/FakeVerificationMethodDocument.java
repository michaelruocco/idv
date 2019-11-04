package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentMother;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultDocument;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class FakeVerificationMethodDocument extends VerificationMethodDocument {

    public FakeVerificationMethodDocument() {
        this(Collections.emptyList());
    }

    public FakeVerificationMethodDocument(final Collection<VerificationResultDocument> results) {
        setName("fake-method");
        setEligibility(EligibilityDocumentMother.eligible());
        setDuration(Duration.ofMinutes(5).toMillis());
        setMaxAttempts(2);
        setResults(results);
    }

}
