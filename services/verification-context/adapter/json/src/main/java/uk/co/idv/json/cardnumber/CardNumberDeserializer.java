package uk.co.idv.json.cardnumber;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardType;

import java.io.IOException;

public class CardNumberDeserializer extends StdDeserializer<CardNumber> {

    public CardNumberDeserializer() {
        super(CardNumber.class);
    }

    @Override
    public CardNumber deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new CardNumber(
                node.get("tokenized").asText(),
                CardType.valueOf(node.get("type").asText())
        );
    }

}
