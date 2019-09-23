package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

import java.io.IOException;

public class AliasDeserializer extends StdDeserializer<Alias> {

    AliasDeserializer() {
        super(Alias.class);
    }

    @Override
    public Alias deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String type = extractType(node);
        switch (type) {
            case CreditCardNumber.TYPE: return new CreditCardNumber(extractValue(node));
            case DebitCardNumber.TYPE: return new DebitCardNumber(extractValue(node));
            case IdvId.TYPE: return new IdvId(extractValue(node));
            default: throw new AliasNotSupportedException(type);
        }
    }

    private static String extractType(final JsonNode node) {
        return node.get("type").asText();
    }

    private static String extractValue(final JsonNode node) {
        return node.get("value").asText();
    }

    public static class AliasNotSupportedException extends RuntimeException {

        private AliasNotSupportedException(final String type) {
            super(type);
        }

    }

}
