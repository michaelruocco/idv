package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import org.javamoney.moneta.Money;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import javax.money.MonetaryAmount;

public class JsonNodeToOnlinePurchaseConverter implements JsonNodeToActivityConverter {

    public Activity toActivity(final JsonNode node) {
        return OnlinePurchase.builder()
                .timestamp(JsonNodeToActivityConverter.extractTimestamp(node))
                .merchantName(extractMerchantName(node))
                .reference(extractReference(node))
                .cost(extractCost(node))
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

}
