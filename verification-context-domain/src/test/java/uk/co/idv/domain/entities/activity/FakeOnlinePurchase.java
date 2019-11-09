package uk.co.idv.domain.entities.activity;

import org.javamoney.moneta.Money;

import java.time.Instant;

public class FakeOnlinePurchase extends OnlinePurchase {

    public FakeOnlinePurchase() {
        super(Instant.parse("2019-09-21T20:43:32.187972Z"),
                "Amazon",
                "ABC-123",
                Money.of(10.99, "GBP")
        );
    }

}
