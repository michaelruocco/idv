package uk.co.idv.uk.domain.entities.channel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.domain.entities.channel.SimpleChannel;

import java.util.Optional;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Rsa extends SimpleChannel {

    public static final String ID = "RSA";

    private final String issuerSessionId;

    public Rsa() {
        this(null);
    }

    public Rsa(final String issuerSessionId) {
        super(ID);
        this.issuerSessionId = issuerSessionId;
    }

    public Optional<String> getIssuerSessionId() {
        return Optional.ofNullable(issuerSessionId);
    }

}
