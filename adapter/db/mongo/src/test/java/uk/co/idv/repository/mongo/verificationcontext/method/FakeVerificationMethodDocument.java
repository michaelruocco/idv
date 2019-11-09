package uk.co.idv.repository.mongo.verificationcontext.method;

import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentMother;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;

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
