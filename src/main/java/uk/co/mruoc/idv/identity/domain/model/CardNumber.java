package uk.co.mruoc.idv.identity.domain.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class CardNumber implements Alias {

    private final String type;
    private final String value;

    public CardNumber(final String type, final String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isCardNumber() {
        return true;
    }

}
