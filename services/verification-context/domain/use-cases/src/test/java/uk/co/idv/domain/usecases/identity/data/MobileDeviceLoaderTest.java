package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MobileDeviceLoaderTest {

    private final Instant now = Instant.now();
    private final TimeProvider timeProvider = new FakeTimeProvider(now);

    private final MobileDeviceLoader loader = new MobileDeviceLoader(timeProvider);

    @Test
    void shouldReturnTrustedDeviceLoggedInFiveMinutesAgoIfAliasValueEndsInNine() {
        final Alias alias = AliasesMother.debitCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<MobileDevice> devices = loader.load(request);

        final MobileDevice expectedDevice = MobileDeviceMother.trusted(now.minus(Duration.ofMinutes(5)));
        assertThat(devices).containsExactly(expectedDevice);
    }

    @Test
    void shouldReturnEmptyListIfAliasValueDoesNotEndInNine() {
        final Alias alias = AliasesMother.debitCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<MobileDevice> devices = loader.load(request);

        assertThat(devices).isEmpty();
    }

}
