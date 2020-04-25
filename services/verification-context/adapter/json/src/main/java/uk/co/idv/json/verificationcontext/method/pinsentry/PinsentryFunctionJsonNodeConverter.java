package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

public class PinsentryFunctionJsonNodeConverter {

    private PinsentryFunctionJsonNodeConverter() {
        //utility class
    }

    public static PinsentryFunction extractFunction(final JsonNode node) {
        return PinsentryFunction.valueOf(node.get("function").asText().toUpperCase());
    }

}
