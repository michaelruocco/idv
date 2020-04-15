package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

public interface PinsentryVerificationMethodJsonNodeConverter extends VerificationMethodJsonNodeConverter {

    static PinsentryFunction extractFunction(final JsonNode node) {
        return PinsentryFunction.valueOf(node.get("function").asText().toUpperCase());
    }

}
