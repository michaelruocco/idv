package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class MobileNumber {

    private static final int NUMBER_OF_UNMASKED_CHARS = 3;

    private final UUID id;
    private final String number;

    public MobileNumber(final String number) {
        this(UUID.randomUUID(), number);
    }

}
