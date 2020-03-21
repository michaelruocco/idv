package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.usecases.util.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationFactoryTest {

    private final UUID id = UUID.randomUUID();
    private final Instant now = Instant.now();

    private final IdGenerator idGenerator = new FakeIdGenerator(id);
    private final TimeGenerator timeGenerator = new FakeTimeGenerator(now);

    private final OneTimePasscodeVerificationFactory factory = OneTimePasscodeVerificationFactory.builder()
            .idGenerator(idGenerator)
            .timeGenerator(timeGenerator)
            .build();

    @Test
    void shouldPopulateId() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldPopulateContextId() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getContextId()).isEqualTo(context.getId());
    }

    @Test
    void shouldPopulateCreated() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getCreated()).isEqualTo(now);
    }

    @Test
    void shouldPopulateExpired() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getExpiry()).isEqualTo(now.plus(method.getDuration()));
    }

    @Test
    void shouldPopulateMaxAttempts() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getMaxAttempts()).isEqualTo(method.getMaxAttempts());
    }

    @Test
    void shouldPopulateMaxDeliveryAttempts() {
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible();
        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);

        final OneTimePasscodeVerification verification = factory.build(context);

        assertThat(verification.getMaxDeliveryAttempts()).isEqualTo(method.getMaxDeliveryAttempts());
    }

}