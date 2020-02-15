package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class CardNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 4;

    private final UUID id;
    private final String tokenized;
    private final CardType type;

    public CardNumber(final String tokenized, final CardType type) {
        this(UUID.randomUUID(), tokenized, type);
    }

}
