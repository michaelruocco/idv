package uk.co.idv.json.verificationcontext.method.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class EligibilityDeserializer extends StdDeserializer<Eligibility> {

    public EligibilityDeserializer() {
        super(Eligibility.class);
    }

    @Override
    public Eligibility deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final boolean eligible = isEligible(node);
        if (eligible) {
            return new Eligible();
        }
        return new Ineligible(node.get("reason").asText());
    }

    public static boolean isEligible(final JsonNode node) {
        return node.get("eligible").asBoolean();
    }

}
