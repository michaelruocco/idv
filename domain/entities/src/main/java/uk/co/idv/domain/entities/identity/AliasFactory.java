package uk.co.idv.domain.entities.identity;

public class AliasFactory {

    public Alias build(final String aliasType, final String aliasValue) {
        switch(aliasType) {
            case IdvId.TYPE: return new IdvId(aliasValue);
            case CreditCardNumber.TYPE: return new CreditCardNumber(aliasValue);
            case DebitCardNumber.TYPE: return new DebitCardNumber(aliasValue);
            default: throw new AliasTypeNotSupportedException(aliasType);
        }
    }

    public static class AliasTypeNotSupportedException extends RuntimeException {

        public AliasTypeNotSupportedException(final String aliasType) {
            super(aliasType);
        }

    }

}
