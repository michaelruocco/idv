package uk.co.idv.domain.usecases.onetimepasscode.send;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationContextLoader;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationFactory;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationLoader;
import uk.co.idv.domain.usecases.onetimepasscode.generator.PasscodeGenerator;
import uk.co.idv.domain.usecases.onetimepasscode.generator.RandomPasscodeGenerator;
import uk.co.idv.domain.usecases.onetimepasscode.message.OneTimePasscodeMessageBuilder;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;
import java.util.UUID;

@Builder
public class OneTimePasscodeSender {

    @Builder.Default
    private final PasscodeGenerator passcodeGenerator = new RandomPasscodeGenerator();

    @Builder.Default
    private final TimeProvider timeProvider = new CurrentTimeProvider();

    private final OneTimePasscodeVerificationContextLoader contextLoader;
    private final OneTimePasscodeVerificationFactory verificationFactory;
    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final OneTimePasscodeMessageBuilder messageBuilder;
    private final OneTimePasscodeDeliverySender sender;
    private final OneTimePasscodeVerificationDao dao;

    public OneTimePasscodeVerification send(final SendOneTimePasscodeRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationFactory.build(context);
        return send(context, verification, request.getDeliveryMethodId());
    }

    public OneTimePasscodeVerification send(final ResendOneTimePasscodeRequest request) {
        final VerificationContext context = contextLoader.load(request.getContextId());
        final OneTimePasscodeVerification verification = verificationLoader.load(request.getId());
        return send(context, verification, request.getDeliveryMethodId());
    }

    private OneTimePasscodeVerification send(final VerificationContext context,
                                             final OneTimePasscodeVerification verification,
                                             final UUID deliveryMethodId) {
        final OneTimePasscode method = context.getNextOneTimePasscodeEligibleMethod();
        final OneTimePasscodeDelivery delivery = toDelivery(deliveryMethodId, method, context.getActivity());
        sender.send(delivery);
        return update(verification, delivery);
    }

    private OneTimePasscodeDelivery toDelivery(final UUID deliveryMethodId,
                                               final OneTimePasscode method,
                                               final Activity activity) {
        final String passcode = passcodeGenerator.generate(method.getPasscodeLength());
        final Instant sent = timeProvider.now();
        return OneTimePasscodeDelivery.builder()
                .passcode(passcode)
                .method(method.getDeliveryMethod(deliveryMethodId))
                .message(messageBuilder.build(activity, passcode))
                .sent(sent)
                .expiry(sent.plus(method.getPasscodeDuration()))
                .build();
    }

    private OneTimePasscodeVerification update(final OneTimePasscodeVerification verification,
                                               final OneTimePasscodeDelivery delivery) {
        verification.record(delivery);
        dao.save(verification);
        return verification;
    }

}
