package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OneTimePasscodeServiceTest {

    private final VerificationContextLoader contextLoader = mock(VerificationContextLoader.class);
    private final OneTimePasscodeVerificationFactory verificationFactory = mock(OneTimePasscodeVerificationFactory.class);
    private final OneTimePasscodeVerificationLoader verificationLoader = mock(OneTimePasscodeVerificationLoader.class);
    private final PasscodeGenerator passcodeGenerator = mock(PasscodeGenerator.class);
    private final OneTimePasscodeSender sender = mock(OneTimePasscodeSender.class);
    private final OneTimePasscodeVerificationDao dao = mock(OneTimePasscodeVerificationDao.class);

    private final OneTimePasscodeService service = OneTimePasscodeService.builder()
            .contextLoader(contextLoader)
            .verificationFactory(verificationFactory)
            .verificationLoader(verificationLoader)
            .passcodeGenerator(passcodeGenerator)
            .sender(sender)
            .dao(dao)
            .build();

    @Test
    void shouldGenerateVerification() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final GenerateOneTimePasscodeVerificationRequest request = GenerateOneTimePasscodeVerificationRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(method)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        assertThat(verification).isEqualTo(expectedVerification);
        verify(dao).save(verification);
    }

    @Test
    void shouldRecordDeliveryAgainstGeneratedVerificationBeforeSaving() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final GenerateOneTimePasscodeVerificationRequest request = GenerateOneTimePasscodeVerificationRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(method)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .activity(context.getActivity())
                .build();
        final InOrder inOrder = Mockito.inOrder(verification, dao);
        inOrder.verify(verification).record(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldSendPasscodeForGeneratedVerification() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final GenerateOneTimePasscodeVerificationRequest request = GenerateOneTimePasscodeVerificationRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(method)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .activity(context.getActivity())
                .build();
        final InOrder inOrder = Mockito.inOrder(sender, dao);
        inOrder.verify(sender).send(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldLoadVerification() {
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final UpdateOneTimePasscodeVerificationRequest request = UpdateOneTimePasscodeVerificationRequest.builder()
                .id(id)
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(id)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        assertThat(verification).isEqualTo(expectedVerification);
        verify(dao).save(verification);
    }

    @Test
    void shouldRecordDeliveryAgainstLoadedVerificationBeforeSaving() {
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final UpdateOneTimePasscodeVerificationRequest request = UpdateOneTimePasscodeVerificationRequest.builder()
                .id(id)
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(id)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .activity(context.getActivity())
                .build();
        final InOrder inOrder = Mockito.inOrder(verification, dao);
        inOrder.verify(verification).record(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldSendPasscodeForLoadedVerification() {
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscodeEligible method = OneTimePasscodeMother.eligible(deliveryMethod);
        final UpdateOneTimePasscodeVerificationRequest request = UpdateOneTimePasscodeVerificationRequest.builder()
                .id(id)
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(id)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);

        final OneTimePasscodeVerification verification = service.sendPasscode(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .activity(context.getActivity())
                .build();
        final InOrder inOrder = Mockito.inOrder(sender, dao);
        inOrder.verify(sender).send(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

}
