package uk.co.idv.domain.entities.card.account;

import uk.co.idv.domain.entities.card.number.CardNumber;

import java.util.Collection;

public interface Account {

    String getStatus();

    Collection<CardNumber> getCardNumbers();

}
