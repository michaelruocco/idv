package uk.co.idv.json.identity.alias.debitcardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class DebitCardNumberDeserializer extends StdDeserializer<DebitCardNumber> {

    public DebitCardNumberDeserializer() {
        super(DebitCardNumber.class);
    }

    @Override
    public DebitCardNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new DebitCardNumber(node.get("value").asText());
    }

}
