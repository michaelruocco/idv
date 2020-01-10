package uk.co.idv.repository.dynamo.verificationcontext;

import uk.co.idv.repository.dynamo.SimpleCreateTableRequestFactory;

public class VerificationContextCreateTableRequestFactory extends SimpleCreateTableRequestFactory {

    public VerificationContextCreateTableRequestFactory(final String environment) {
        super(environment + "-verification-context");
    }

}
