package uk.co.idv.domain.entities.card.account;

import lombok.EqualsAndHashCode;
import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
public class ClosedAccount extends Account {

    public ClosedAccount(final CardNumber... cardNumbers) {
        this(Arrays.asList(cardNumbers));
    }

    public ClosedAccount(final Collection<CardNumber> cardNumbers) {
        super(AccountStatus.CLOSED, cardNumbers);
    }

}
