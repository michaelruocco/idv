package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

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
