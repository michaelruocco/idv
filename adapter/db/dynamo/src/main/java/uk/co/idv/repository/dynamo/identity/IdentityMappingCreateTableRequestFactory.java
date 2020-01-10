package uk.co.idv.repository.dynamo.lockout.attempt;

import uk.co.idv.repository.dynamo.SimpleCreateTableRequestFactory;

public class VerificationAttemptCreateTableRequestFactory extends SimpleCreateTableRequestFactory {

    public VerificationAttemptCreateTableRequestFactory(final String environment) {
        super(environment + "-verification-attempt");
    }

}
