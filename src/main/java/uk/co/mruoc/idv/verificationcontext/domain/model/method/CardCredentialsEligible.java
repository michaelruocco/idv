package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.DefaultVerificationResults;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;

public class CardCredentialsEligible extends AbstractVerificationMethodEligible implements CardCredentials {

    private static final int MAX_ATTEMPTS = 1;
    private static final Duration DURATION = Duration.ofMinutes(5);

    public CardCredentialsEligible() {
        this(new DefaultVerificationResults());
    }

    public CardCredentialsEligible(final VerificationResults results) {
        super(NAME, results, MAX_ATTEMPTS, DURATION);
    }

    @Override
    protected VerificationMethod updateResults(final VerificationResults results) {
        return new CardCredentialsEligible(results);
    }

}
