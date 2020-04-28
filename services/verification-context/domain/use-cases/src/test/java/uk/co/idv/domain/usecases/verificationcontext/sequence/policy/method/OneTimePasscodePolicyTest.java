package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.NoEligibleDeliveryMethods;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodePolicyTest {

    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final UUID ID = UUID.randomUUID();

    private final PasscodeSettings settings = new DefaultPasscodeSettings();
    private final IdGenerator idGenerator = new FakeIdGenerator(ID);
    private final OneTimePasscodePolicy parameters = new OneTimePasscodePolicy(idGenerator, settings, MAX_ATTEMPTS, DURATION);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneMobilePhoneNumber() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(OneTimePasscodeEligible.class);
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveAnyMobilePhoneNumbers() {
        final PhoneNumber phoneNumber = PhoneNumberMother.other();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(OneTimePasscodeIneligible.class);
    }

    @Test
    void shouldPopulateSmsDeliveryMethodForMobilePhoneNumber() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final OneTimePasscodeEligible method = (OneTimePasscodeEligible) parameters.buildMethod(request);

        assertThat(method.getDeliveryMethods()).containsExactly(new SmsDeliveryMethod(ID, phoneNumber.getValue()));
    }

    @Test
    void shouldPopulateEmptyDeliveryMethodsIfNoPhoneNumbersFound() {
        final Identity identity = IdentityMother.emptyData();
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final OneTimePasscodeIneligible method = (OneTimePasscodeIneligible) parameters.buildMethod(request);

        assertThat(method.getEligibility()).isEqualTo(new NoEligibleDeliveryMethods());
    }

    @Test
    void shouldPopulatePasscodeSettingsIfEligible() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final OneTimePasscodeEligible method = (OneTimePasscodeEligible) parameters.buildMethod(request);

        assertThat(method.getPasscodeSettings()).isEqualTo(settings);
    }

    @Test
    void shouldPopulateMaxAttemptsIfIdentityHasMobilePhoneNumbers() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(MAX_ATTEMPTS);
    }

    @Test
    void shouldPopulateZeroMaxAttemptsIfIdentityDoesNotHaveAnyMobilePhoneNumbers() {
        final PhoneNumber phoneNumber = PhoneNumberMother.other();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldPopulateDurationIfIdentityHasMobilePhoneNumbers() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(DURATION);
    }

    @Test
    void shouldPopulateZeroDurationIfIdentityDoesNotHaveAnyMobilePhoneNumbers() {
        final PhoneNumber phoneNumber = PhoneNumberMother.other();
        final Identity identity = IdentityMother.withPhoneNumbers(phoneNumber);
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

}
