package uk.co.idv.repository.dynamo.verificationcontext;

import uk.co.idv.repository.dynamo.table.SimpleCreateTableRequest;

public class VerificationContextCreateTableRequest extends SimpleCreateTableRequest {

    public VerificationContextCreateTableRequest(final String environment) {
        super(toTableName(environment));
    }

    private static String toTableName(final String environment) {
        return environment + "-verification-context";
    }

}
