package uk.co.idv.json.activity.onlinepurchase;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.json.activity.ActivityJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import javax.money.MonetaryAmount;
import java.io.IOException;

public class OnlinePurchaseDeserializer extends StdDeserializer<OnlinePurchase> {

    public OnlinePurchaseDeserializer() {
        super(OnlinePurchase.class);
    }

    @Override
    public OnlinePurchase deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return OnlinePurchase.builder()
                .timestamp(ActivityJsonNodeConverter.extractTimestamp(node))
                .merchantName(node.get("merchantName").asText())
                .reference(node.get("reference").asText())
                .cost(extractCost(node, parser))
                .cardNumber(extractCardNumber(node, parser))
                .build();
    }

    private static MonetaryAmount extractCost(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("cost"), parser, MonetaryAmount.class);
    }

    private static CardNumber extractCardNumber(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("cardNumber"), parser, CardNumber.class);
    }

}
