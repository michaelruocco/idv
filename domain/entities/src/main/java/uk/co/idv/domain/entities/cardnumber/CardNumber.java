package uk.co.idv.domain.entities.cardnumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CardNumber {

    private final String tokenized;
    private final CardType type;

}
