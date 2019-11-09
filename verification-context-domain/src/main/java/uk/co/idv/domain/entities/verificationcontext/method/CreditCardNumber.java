package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreditCardNumber extends CardNumber {

    public CreditCardNumber(final String tokenized) {
        this(UUID.randomUUID(), tokenized);
    }

    public CreditCardNumber(final UUID id, final String tokenized) {
        super(id, tokenized, CardType.CREDIT);
    }

}
