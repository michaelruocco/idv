package uk.co.idv.domain.entities.activity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.OnlinePurchase.OnlinePurchaseBuilder;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.cardnumber.CardNumberMother;

import javax.money.MonetaryAmount;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OnlinePurchaseTest {

    private final OnlinePurchaseBuilder builder = OnlinePurchase.builder();

    @Test
    void shouldReturnName() {
        final OnlinePurchase onlinePurchase = builder.build();

        assertThat(onlinePurchase.getName()).isEqualTo("online-purchase");
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final OnlinePurchase onlinePurchase = builder.timestamp(timestamp).build();

        assertThat(onlinePurchase.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnMerchantName() {
        final String merchantName = "merchant-name";

        final OnlinePurchase onlinePurchase = builder.merchantName(merchantName).build();

        assertThat(onlinePurchase.getMerchantName()).isEqualTo(merchantName);
    }

    @Test
    void shouldReturnCost() {
        final MonetaryAmount cost = Money.of(9.99, "GBP");

        final OnlinePurchase onlinePurchase = builder.cost(cost).build();

        assertThat(onlinePurchase.getCost()).isEqualTo(cost);
    }

    @Test
    void shouldReturnReference() {
        final String reference = "reference";

        final OnlinePurchase onlinePurchase = builder.reference(reference).build();

        assertThat(onlinePurchase.getReference()).isEqualTo(reference);
    }

    @Test
    void shouldReturnCardNumber() {
        final CardNumber cardNumber = CardNumberMother.credit();

        final OnlinePurchase onlinePurchase = builder.cardNumber(cardNumber).build();

        assertThat(onlinePurchase.getCardNumber()).isEqualTo(cardNumber);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OnlinePurchase.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
