package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;



public interface VerificationMethodJsonNodeConverter {

    boolean supportsMethod(final String name);

    VerificationMethod toMethod(final JsonNode node, final JsonParser parser, final DeserializationContext context);

    static boolean extractEligible(final JsonNode node) {
        return node.get("eligible").asBoolean();
    }

    static VerificationResults extractResults(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("results"), parser, VerificationResults.class);
    }

}
