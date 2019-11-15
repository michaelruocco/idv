package uk.co.idv.domain.entities.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Rsa extends SimpleChannel {

    public static final String ID = "RSA";

    private final UUID issuerSessionId;

    public Rsa() {
        this(null);
    }

    public Rsa(final UUID issuerSessionId) {
        super(ID);
        this.issuerSessionId = issuerSessionId;
    }

    public Optional<UUID> getIssuerSessionId() {
        return Optional.ofNullable(issuerSessionId);
    }

}
