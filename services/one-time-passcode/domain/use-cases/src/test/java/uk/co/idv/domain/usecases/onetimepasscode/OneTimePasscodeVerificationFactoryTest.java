package uk.co.idv.domain.usecases.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationFactoryTest {

    private final UUID id = UUID.randomUUID();
    private final Instant now = Instant.now();

    private final IdGenerator idGenerator = new FakeIdGenerator(id);
    private final TimeProvider timeProvider = new FakeTimeProvider(now);

    private final OneTimePasscodeVerificationFactory factory = OneTimePasscodeVerificationFactory.builder()
            .idGenerator(idGenerator)
            .timeProvider(timeProvider)
            .build();

    @Test
    void shouldPopulateId() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldPopulateContextId() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateCreated() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getCreated()).isEqualTo(now);
    }

    @Test
    void shouldPopulateExpired() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getExpiry()).isEqualTo(now.plus(method.getDuration()));
    }

    @Test
    void shouldPopulateMaxAttempts() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateMaxDeliveries() {
        final OneTimePasscode method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getMaxDeliveries()).isEqualTo(method.getMaxDeliveries());
    }

}
