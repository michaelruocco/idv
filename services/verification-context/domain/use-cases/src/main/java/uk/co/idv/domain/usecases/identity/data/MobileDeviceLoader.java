package uk.co.idv.domain.usecases.identity.data;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class MobileDeviceLoader {

    private final TimeProvider timeProvider;

    public Collection<MobileDevice> load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (valueEndsWithNine(providedAlias)) {
            return toEligibleDevices();
        }
        return Collections.emptyList();
    }

    private Collection<MobileDevice> toEligibleDevices() {
        final MobileDevice device = MobileDevice.builder()
                .id("1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                .lastLogin(timeProvider.now().minus(Duration.ofMinutes(5)))
                .trusted(true)
                .build();
        return Collections.singleton(device);
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
