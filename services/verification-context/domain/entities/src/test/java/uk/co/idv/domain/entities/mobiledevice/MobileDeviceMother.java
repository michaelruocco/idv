package uk.co.idv.domain.entities.mobiledevice;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MobileDeviceMother {

    public static Collection<MobileDevice> two() {
        return Arrays.asList(trusted(), untrusted());
    }

    public static Collection<MobileDevice> oneTrusted() {
        return Collections.singleton(trusted());
    }

    public static MobileDevice trusted() {
        return trusted(Instant.parse("2020-04-24T05:32:00.428Z"));
    }

    public static MobileDevice untrusted() {
        return untrusted(Instant.parse("2020-04-24T05:27:00.123Z"));
    }

    public static MobileDevice trusted(final Instant lastLogin) {
        return MobileDevice.builder()
                .id("1234567890ABCDEFG")
                .lastLogin(lastLogin)
                .trusted(true)
                .build();
    }

    public static MobileDevice untrusted(final Instant lastLogin) {
        return MobileDevice.builder()
                .id("0987654321ABCXYZ")
                .lastLogin(lastLogin)
                .trusted(false)
                .build();
    }

}
