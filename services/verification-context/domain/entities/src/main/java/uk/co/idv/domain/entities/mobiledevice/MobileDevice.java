package uk.co.idv.domain.entities.mobiledevice;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class MobileDevice {

    private final String id;
    private final Instant lastLogin;
    private final boolean trusted;

    public boolean usedSince(final Instant loginCutoff) {
        return lastLogin.isAfter(loginCutoff);
    }

}
