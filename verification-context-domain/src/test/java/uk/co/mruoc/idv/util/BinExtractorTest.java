package uk.co.mruoc.idv.util;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.util.BinExtractor.CardNumberTooShortForBinException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BinExtractorTest {

    @Test
    void shouldThrowExceptionIfCardNumberLessThanSixDigits() {
        final String cardNumber = "123";

        final Throwable error = catchThrowable(() -> BinExtractor.extractBin(cardNumber));

        assertThat(error)
                .isInstanceOf(CardNumberTooShortForBinException.class)
                .hasMessage("card number 123 is to short to calculate bin, must be at least 6 digits");
    }

    @Test
    void shouldReturnFirstSixDigitsOfCardNumber() {
        final String cardNumber = "1234567890";

        final String bin = BinExtractor.extractBin(cardNumber);

        assertThat(bin).isEqualTo("123456");
    }

}
