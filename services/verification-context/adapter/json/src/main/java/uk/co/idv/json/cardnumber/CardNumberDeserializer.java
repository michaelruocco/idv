package uk.co.idv.json.cardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CreditCardNumber;
import uk.co.idv.domain.entities.card.number.DebitCardNumber;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class CardNumberDeserializer extends StdDeserializer<CardNumber> {

    public CardNumberDeserializer() {
        super(CardNumber.class);
    }

    @Override
    public CardNumber deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = node.get("type").asText();
        final String tokenized = node.get("tokenized").asText();
        switch (type) {
            case CreditCardNumber.TYPE: return new CreditCardNumber(tokenized);
            case DebitCardNumber.TYPE: return new DebitCardNumber(tokenized);
            default: throw new InvalidCardTypeException(type);
        }
    }

    public static class InvalidCardTypeException extends RuntimeException {

        public InvalidCardTypeException(final String type) {
            super(type);
        }

    }

}
