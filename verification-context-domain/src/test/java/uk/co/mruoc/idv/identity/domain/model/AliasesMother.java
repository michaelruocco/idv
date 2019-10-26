package uk.co.mruoc.idv.identity.domain.model;

public class AliasesMother {

    private AliasesMother() {
        // utility class
    }

    public static Aliases aliases() {
        return Aliases.with(
                idvId(),
                creditCardNumber()
        );
    }

    public static IdvId idvId() {
        return new IdvId("ee69bf61-89a1-46c3-a0c6-ba44b747a286");
    }

    public static Alias creditCardNumber() {
        return creditCardNumber("4929001111111111");
    }

    public static Alias creditCardNumber(final String tokenized) {
        return new CreditCardNumber(tokenized);
    }

    public static Alias debitCardNumber() {
        return new DebitCardNumber("4929992222222222");
    }

}
