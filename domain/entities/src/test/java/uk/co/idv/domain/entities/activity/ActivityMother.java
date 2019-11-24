package uk.co.idv.domain.entities.activity;

import org.javamoney.moneta.Money;

import java.time.Instant;

public class ActivityMother {

    public static Activity fake() {
        return SimpleActivity.builder()
                .name("fake-activity")
                .timestamp(timestamp())
                .build();
    }

    public static OnlinePurchase onlinePurchase() {
        return OnlinePurchase.builder()
                .timestamp(timestamp())
                .merchantName("Amazon")
                .reference("ABC-123")
                .cost(Money.of(10.99, "GBP"))
                .build();
    }

    public static Activity login() {
        return new Login(timestamp());
    }

    private static Instant timestamp() {
        return Instant.parse("2019-09-21T20:43:32.187972Z");
    }
}