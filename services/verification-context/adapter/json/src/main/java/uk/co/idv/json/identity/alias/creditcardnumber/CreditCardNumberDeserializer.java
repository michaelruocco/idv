package uk.co.idv.json.identity.alias.creditcardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class CreditCardNumberDeserializer extends StdDeserializer<CreditCardNumber> {

    public CreditCardNumberDeserializer() {
        super(CreditCardNumber.class);
    }

    @Override
    public CreditCardNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new CreditCardNumber(node.get("value").asText());
    }

}
