package uk.co.idv.json.activity.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.json.activity.ActivityJsonNodeConverter;

import javax.money.MonetaryAmount;
import java.io.IOException;

@Slf4j
public class OnlinePurchaseJsonNodeConverter implements ActivityJsonNodeConverter {

    @Override
    public boolean supportsActivity(String name) {
        boolean supported = OnlinePurchase.NAME.equals(name);
        log.info("returning supported {} for activity name {}", supported, name);
        return supported;
    }

    @Override
    public Activity toActivity(final JsonNode node,
                               final JsonParser parser,
                               final DeserializationContext context) throws IOException {
        return OnlinePurchase.builder()
                .timestamp(ActivityJsonNodeConverter.extractTimestamp(node))
                .merchantName(extractMerchantName(node))
                .reference(extractReference(node))
                .cost(extractCost(node))
                .cardNumber(extractCardNumber(node, parser))
                .build();
    }

    private static String extractMerchantName(final JsonNode node) {
        return node.get("merchantName").asText();
    }

    private static String extractReference(final JsonNode node) {
        return node.get("reference").asText();
    }

    private static MonetaryAmount extractCost(final JsonNode node) {
        final JsonNode costNode = node.get("cost");
        return Money.of(
                costNode.get("amount").numberValue(),
                costNode.get("currency").asText()
        );
    }

    private static CardNumber extractCardNumber(final JsonNode node, final JsonParser parser) throws IOException {
        return node.get("cardNumber").traverse(parser.getCodec()).readValueAs(CardNumber.class);
    }

}
