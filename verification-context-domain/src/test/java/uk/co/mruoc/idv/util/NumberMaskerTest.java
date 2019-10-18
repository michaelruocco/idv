package uk.co.mruoc.idv.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberMaskerTest {

    private static final int NUMBER_OF_UNMASKED_CHARACTERS = 4;

    private static final String NUMBER = "1234567890123456";
    private static final String MASKED = "************3456";

    @Test
    void shouldMaskCardNumberLeavingSpecifiedNumberOfCharactersUnmasked() {
        final String masked = NumberMasker.mask(NUMBER, NUMBER_OF_UNMASKED_CHARACTERS);

        assertThat(masked).isEqualTo(MASKED);
    }

    @Test
    void shouldReturnInputIfNoCharactersToMask() {
        final String shortNumber = "1234";

        final String masked = NumberMasker.mask(shortNumber, NUMBER_OF_UNMASKED_CHARACTERS);

        assertThat(masked).isEqualTo(shortNumber);
    }

}
