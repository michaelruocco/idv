package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardType;

import java.io.IOException;
import java.util.UUID;

public class CardNumberDeserializer extends StdDeserializer<CardNumber> {

    public CardNumberDeserializer() {
        super(CardNumber.class);
    }

    @Override
    public CardNumber deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new CardNumber(
                UUID.fromString(node.get("id").asText()),
                node.get("tokenized").asText(),
                CardType.valueOf(node.get("type").asText())
        );
    }

}
