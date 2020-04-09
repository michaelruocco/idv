package uk.co.idv.repository.dynamo.onetimepasscode;

import uk.co.idv.dynamo.table.SimpleCreateTableRequest;

public class OneTimePasscodeVerificationCreateTableRequest extends SimpleCreateTableRequest {

    public OneTimePasscodeVerificationCreateTableRequest(final String environment) {
        super(toTableName(environment));
    }

    private static String toTableName(final String environment) {
        return environment + "-one-time-passcode-verification";
    }

}
