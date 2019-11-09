package uk.co.idv.domain.entities.identity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
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
