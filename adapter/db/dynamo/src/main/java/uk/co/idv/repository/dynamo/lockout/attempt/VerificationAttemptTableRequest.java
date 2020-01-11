package uk.co.idv.repository.dynamo.lockout.attempt;

import uk.co.idv.repository.dynamo.SimpleCreateTableRequest;

public class VerificationAttemptTableRequest extends SimpleCreateTableRequest {

    public VerificationAttemptTableRequest(final String environment) {
        super(toTableName(environment));
    }

    private static String toTableName(final String environment) {
        return environment + "-verification-attempt";
    }

}
