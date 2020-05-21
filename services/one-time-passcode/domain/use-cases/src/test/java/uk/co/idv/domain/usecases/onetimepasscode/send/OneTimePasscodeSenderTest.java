package uk.co.idv.domain.usecases.onetimepasscode.send;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationContextLoader;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OneTimePasscodeSenderTest {

    private final Instant now = Instant.now();

    private final OneTimePasscodeVerificationContextLoader contextLoader = mock(OneTimePasscodeVerificationContextLoader.class);
    private final OneTimePasscodeVerificationFactory verificationFactory = mock(OneTimePasscodeVerificationFactory.class);
    private final OneTimePasscodeVerificationLoader verificationLoader = mock(OneTimePasscodeVerificationLoader.class);
    private final PasscodeGenerator passcodeGenerator = mock(PasscodeGenerator.class);
    private final OneTimePasscodeMessageBuilder messageBuilder = mock(OneTimePasscodeMessageBuilder.class);
    private final TimeProvider timeProvider = mock(TimeProvider.class);
    private final OneTimePasscodeDeliverySender deliverySender = mock(OneTimePasscodeDeliverySender.class);
    private final OneTimePasscodeVerificationDao dao = mock(OneTimePasscodeVerificationDao.class);

    private final OneTimePasscodeSender sender = OneTimePasscodeSender.builder()
            .contextLoader(contextLoader)
            .verificationFactory(verificationFactory)
            .verificationLoader(verificationLoader)
            .passcodeGenerator(passcodeGenerator)
            .messageBuilder(messageBuilder)
            .timeProvider(timeProvider)
            .sender(deliverySender)
            .dao(dao)
            .build();

    @BeforeEach
    public void setUp() {
        given(timeProvider.now()).willReturn(now);
    }

    @Test
    void shouldCreateVerification() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(context)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = sender.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
        verify(dao).save(verification);
    }

    @Test
    void shouldRecordDeliveryAgainstCreatedVerificationBeforeSaving() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(context)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);
        final String message = "message";
        given(messageBuilder.build(context.getActivity(), passcode)).willReturn(message);

        final OneTimePasscodeVerification verification = sender.send(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .message(message)
                .sent(now)
                .expiry(now.plus(method.getPasscodeDuration()))
                .build();
        final InOrder inOrder = Mockito.inOrder(verification, dao);
        inOrder.verify(verification).record(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldSendPasscodeForCreatedVerification() {
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final SendOneTimePasscodeRequest request = SendOneTimePasscodeRequest.builder()
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationFactory.build(context)).willReturn(expectedVerification);
        final String passcode = "passcode";
        given(passcodeGenerator.generate(method.getPasscodeLength())).willReturn(passcode);
        final String message = "message";
        given(messageBuilder.build(context.getActivity(), passcode)).willReturn(message);

        final OneTimePasscodeVerification verification = sender.send(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .message(message)
                .sent(now)
                .expiry(now.plus(method.getPasscodeDuration()))
                .build();
        final InOrder inOrder = Mockito.inOrder(deliverySender, dao);
        inOrder.verify(deliverySender).send(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

    @Test
    void shouldLoadVerificationWhenUpdating() {
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequest.builder()
                .id(id)
                .contextId(contextId)
                .deliveryMethodId(deliveryMethod.getId())
                .build();

        final VerificationContext context = VerificationContextMother.withNextEligibleMethod(method);
        given(contextLoader.load(contextId)).willReturn(context);
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(verificationLoader.load(id)).willReturn(expectedVerification);

        final OneTimePasscodeVerification verification = sender.send(request);

        assertThat(verification).isEqualTo(expectedVerification);
        verify(dao).save(verification);
    }

    @Test
    void shouldRecordDeliveryAgainstLoadedVerificationBeforeSaving() {
        final UUID id = UUID.randomUUID();
        final UUID contextId = UUID.randomUUID();
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequest.builder()
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
        final String message = "message";
        given(messageBuilder.build(context.getActivity(), passcode)).willReturn(message);

        final OneTimePasscodeVerification verification = sender.send(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .message(message)
                .sent(now)
                .expiry(now.plus(method.getPasscodeDuration()))
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
        final OneTimePasscode method = OneTimePasscodeMother.eligible(deliveryMethod);
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequest.builder()
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
        final String message = "message";
        given(messageBuilder.build(context.getActivity(), passcode)).willReturn(message);

        final OneTimePasscodeVerification verification = sender.send(request);

        final OneTimePasscodeDelivery expectedDelivery = OneTimePasscodeDelivery.builder()
                .method(deliveryMethod)
                .passcode(passcode)
                .message(message)
                .sent(now)
                .expiry(now.plus(method.getPasscodeDuration()))
                .build();
        final InOrder inOrder = Mockito.inOrder(deliverySender, dao);
        inOrder.verify(deliverySender).send(expectedDelivery);
        inOrder.verify(dao).save(verification);
    }

}
