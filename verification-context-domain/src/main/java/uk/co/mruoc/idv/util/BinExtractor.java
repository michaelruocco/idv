package uk.co.mruoc.idv.util;

public class BinExtractor {

    private static final int BIN_LENGTH = 6;

    public static String extractBin(final String cardNumber) {
        if (cardNumber.length() < BIN_LENGTH) {
            throw new CardNumberTooShortForBinException(cardNumber);
        }
        return cardNumber.substring(0, BIN_LENGTH);
    }

    public static class CardNumberTooShortForBinException extends RuntimeException {

        private static final String MESSAGE = "card number %s is to short to calculate bin, must be at least %d digits";

        private CardNumberTooShortForBinException(final String cardNumber) {
            super(String.format(MESSAGE, cardNumber, BIN_LENGTH));
        }

    }

}
