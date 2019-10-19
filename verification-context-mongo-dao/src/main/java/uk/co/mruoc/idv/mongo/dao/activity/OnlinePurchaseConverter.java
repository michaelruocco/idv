package uk.co.mruoc.idv.mongo.dao.activity;

import org.javamoney.moneta.Money;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class OnlinePurchaseConverter implements ActivityConverter {

    private static final String MERCHANT_NAME_KEY = "merchantName";
    private static final String REFERENCE_KEY = "reference";
    private static final String COST_AMOUNT_KEY = "costAmount";
    private static final String COST_CURRENCY_CODE_KEY = "costCurrencyCode";

    @Override
    public String getSupportedActivityName() {
        return OnlinePurchase.NAME;
    }

    @Override
    public Activity toActivity(final ActivityDocument document) {
        final Map<String, String> properties = document.getProperties();
        return OnlinePurchase.builder()
                .timestamp(Instant.parse(document.getTimestamp()))
                .merchantName(extractMerchantName(properties))
                .reference(extractReference(properties))
                .cost(extractCost(properties))
                .build();
    }

    @Override
    public ActivityDocument toDocument(final Activity activity) {
        return ActivityDocument.builder()
                .name(activity.getName())
                .timestamp(activity.getTimestamp().toString())
                .properties(toProperties(activity))
                .build();
    }

    private Map<String, String> toProperties(final Activity activity) {
        return toProperties((OnlinePurchase) activity);
    }

    private Map<String, String> toProperties(final OnlinePurchase onlinePurchase) {
        final Map<String, String> properties = new HashMap<>();
        properties.put(MERCHANT_NAME_KEY, onlinePurchase.getMerchantName());
        properties.put(REFERENCE_KEY, onlinePurchase.getReference());
        final MonetaryAmount cost = onlinePurchase.getCost();
        properties.put(COST_AMOUNT_KEY, cost.getNumber().toString());
        properties.put(COST_CURRENCY_CODE_KEY, cost.getCurrency().getCurrencyCode());
        return properties;
    }

    private static String extractMerchantName(final Map<String, String> properties) {
        return properties.get(MERCHANT_NAME_KEY);
    }

    private static String extractReference(final Map<String, String> properties) {
        return properties.get(REFERENCE_KEY);
    }

    private static MonetaryAmount extractCost(final Map<String, String> properties) {
        final BigDecimal amount = new BigDecimal(properties.get(COST_AMOUNT_KEY));
        final String currencyCode = properties.get(COST_CURRENCY_CODE_KEY);
        return Money.of(amount, currencyCode);
    }

}
