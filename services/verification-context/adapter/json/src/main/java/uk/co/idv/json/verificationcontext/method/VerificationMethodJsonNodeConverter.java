package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.util.Optional;

public class VerificationMethodJsonNodeConverter {

    private VerificationMethodJsonNodeConverter() {
        //utility class
    }

    public static VerificationResults toResults(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("results"))
                .map(results -> JsonNodeConverter.toObject(results, parser, VerificationResults.class))
                .orElseGet(DefaultVerificationResults::new);
    }

    public static Eligibility toEligibility(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node, parser, Eligibility.class);
    }

}
