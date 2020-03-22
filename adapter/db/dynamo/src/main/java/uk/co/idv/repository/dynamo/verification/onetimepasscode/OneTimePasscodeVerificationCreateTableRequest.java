package uk.co.idv.repository.dynamo.verification.onetimepasscode;

import uk.co.idv.repository.dynamo.table.SimpleCreateTableRequest;

public class OneTimePasscodeVerificationCreateTableRequest extends SimpleCreateTableRequest {

    public OneTimePasscodeVerificationCreateTableRequest(final String environment) {
        super(toTableName(environment));
    }

    private static String toTableName(final String environment) {
        return environment + "-one-time-passcode-verification";
    }

}
