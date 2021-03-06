package uk.co.idv.domain.entities.identity.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
public class IdvId implements Alias {

    public static final String TYPE = "idv-id";

    private final UUID value;

    public IdvId(final String value) {
        this(UUID.fromString(value));
    }

    public IdvId(final UUID value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

    public UUID getValueAsUuid() {
        return value;
    }

    public static boolean isIdvId(final Alias alias) {
        return TYPE.equals(alias.getType());
    }

}
