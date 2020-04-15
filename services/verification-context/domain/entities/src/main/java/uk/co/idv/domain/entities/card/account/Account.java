package uk.co.idv.domain.entities.card.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class Account {

    private final AccountStatus status;
    private final Collection<CardNumber> cardNumbers;

}
