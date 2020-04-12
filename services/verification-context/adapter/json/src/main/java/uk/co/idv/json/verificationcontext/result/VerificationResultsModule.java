package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class VerificationResultsModule extends SimpleModule {

    public VerificationResultsModule() {
        super("verification-context-results-module", Version.unknownVersion());

        addSerializer(VerificationResults.class, new VerificationResultsSerializer());
        addSerializer(VerificationResult.class, new VerificationResultSerializer());

        addDeserializer(VerificationResult.class, new VerificationResultDeserializer());
        addDeserializer(VerificationResults.class, new VerificationResultsDeserializer());
    }

}
