package uk.co.idv.domain.entities.identity;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
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

}
