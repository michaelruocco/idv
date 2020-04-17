package uk.co.idv.domain.entities.card.number;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CardNumber {

    private final String type;
    private final String tokenized;

}
