package uk.co.idv.domain.entities.mobiledevice;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public class MobileDeviceMother {

    public static Collection<MobileDevice> oneTrusted() {
        return Collections.singleton(trusted());
    }

    public static MobileDevice trusted() {
        return trusted(Instant.now());
    }

    public static MobileDevice trusted(final Instant lastLogin) {
        return MobileDevice.builder()
                .id("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                .lastLogin(lastLogin)
                .trusted(true)
                .build();
    }

}
