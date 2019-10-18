package uk.co.mruoc.idv.api.activity;

import com.fasterxml.jackson.databind.JsonNode;
import org.javamoney.moneta.Money;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;

import javax.money.MonetaryAmount;
import java.time.Instant;

public class JsonNodeToOnlinePurchaseConverter implements JsonNodeToActivityConverter {

    public Activity toActivity(final JsonNode node) {
        return OnlinePurchase.builder()
                .timestamp(extractTimestamp(node))
                .merchantName(extractMerchantName(node))
                .reference(extractReference(node))
                .cost(extractCost(node))
                .build();
    }

    private static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
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
