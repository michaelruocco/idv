package uk.co.idv.domain.entities.identity.alias;


import java.util.UUID;

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

    public static IdvId randomIdvId() {
        return idvId(UUID.randomUUID().toString());
    }

    public static IdvId idvId() {
        return idvId("ee69bf61-89a1-46c3-a0c6-ba44b747a286");
    }

    public static IdvId idvId(final String value) {
        return new IdvId(value);
    }

    public static Alias creditCardNumber() {
        return creditCardNumber("4929001111111111");
    }

    public static Alias creditCardNumber(final String tokenized) {
        return new CreditCardNumber(tokenized);
    }

    public static Alias debitCardNumber() {
        return debitCardNumber("4929992222222222");
    }

    public static Alias debitCardNumber(final String tokenized) {
        return new DebitCardNumber(tokenized);
    }

    public static Alias fake() {
        return new FakeAlias();
    }

    private static class FakeAlias implements Alias {

        @Override
        public String getType() {
            return "fake-alias-type";
        }

        @Override
        public String getValue() {
            return "fake-value";
        }

        @Override
        public boolean isCardNumber() {
            return false;
        }

    }

}
