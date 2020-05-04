package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

public class PinsentryFunctionJsonNodeConverter {

    private PinsentryFunctionJsonNodeConverter() {
        //utility class
    }

    public static PinsentryParams extractParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, PinsentryParams.class);
    }

    public static PinsentryFunction extractFunction(final JsonNode node) {
        return PinsentryFunction.valueOf(node.get("function").asText().toUpperCase());
    }

}
