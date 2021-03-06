package uk.co.idv.domain.entities.identity.alias;

public interface Alias {

    String getType();

    String getValue();

    boolean isCardNumber();

    default boolean isType(final String type) {
        return getType().equals(type);
    }

    class AliasWithTypeNotFoundException extends RuntimeException {

        public AliasWithTypeNotFoundException(final String type) {
            super(type);
        }

    }

}
