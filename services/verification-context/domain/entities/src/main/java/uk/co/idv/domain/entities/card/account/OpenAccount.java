package uk.co.idv.domain.entities.card.account;

import lombok.EqualsAndHashCode;
import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class OpenAccount extends Account {

    public OpenAccount(final CardNumber... cardNumbers) {
        this(Arrays.asList(cardNumbers));
    }

    public OpenAccount(final Collection<CardNumber> cardNumbers) {
        super(AccountStatus.OPEN, cardNumbers);
    }

}
