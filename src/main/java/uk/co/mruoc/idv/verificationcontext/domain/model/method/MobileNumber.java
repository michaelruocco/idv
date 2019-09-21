package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.util.NumberMasker;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class MobileNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 3;

    private final UUID id;
    private final String number;

    public MobileNumber(final String number) {
        this(UUID.randomUUID(), number);
    }

    public String getMasked() {
        return NumberMasker.mask(number, NUMBER_OF_UNMASKED_CHARS);
    }

}
