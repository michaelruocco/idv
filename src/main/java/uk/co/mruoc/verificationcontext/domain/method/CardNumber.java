package uk.co.mruoc.verificationcontext.domain.method;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.verificationcontext.util.NumberMasker;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class CardNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 4;

    private final UUID id;
    private final String tokenized;

    public CardNumber(final String tokenized) {
        this(UUID.randomUUID(), tokenized);
    }
    
    public String getMasked() {
        return NumberMasker.mask(tokenized, NUMBER_OF_UNMASKED_CHARS);
    }

}