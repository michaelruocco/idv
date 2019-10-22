package uk.co.mruoc.idv.verificationcontext.domain.model.method;

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
